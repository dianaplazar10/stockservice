package com.stocks.stockservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.stocks.stockservice.dto.AppConstants;
import com.stocks.stockservice.model.Holdings;
import com.stocks.stockservice.model.Stock;
import com.stocks.stockservice.model.UserStkNotifMapping;
import com.stocks.stockservice.repository.HoldingsRepository;
import com.stocks.stockservice.repository.UserStkNotifMappingRepository;
import com.stocks.stockservice.utils.HoldingsUtil;

@Component
public class UserStockNotifServiceImpl implements UserStockNotifService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserStockNotifServiceImpl.class);

	@Autowired
	private UserStkNotifMappingRepository userStkNotifMappingRepository;
	
	@Autowired
	private HoldingsService holdingsService;
	
	@Autowired
	private StockService stockService;
	
	private HoldingsUtil holdingsUtil = new HoldingsUtil();

	/*
	@Override
	public void addStocks(int userId, List<Integer> stkIds) {
		List<UserStkNotifMapping> listMappings = new ArrayList<UserStkNotifMapping>();
		//check if mappings already exist, if exists, then increment count in holdings DB
		stkIds = filterOutAlreadyAddedStocks(userId, stkIds);
		
		listMappings = updateUSNmappings(userId, stkIds, listMappings);
		if(!listMappings.isEmpty()) {
			userStkNotifMappingRepository.saveAll(listMappings);
		}
	}
	*/
	
	@Override
	public void addStockToUser(int userId, int stkId, int stkCount) {
		List<UserStkNotifMapping> existingStocks = userStkNotifMappingRepository.findByUserIdAndStkId((long) userId,(long) stkId);
		Stock stock = stockService.getStock(stkId);
		if(existingStocks.isEmpty()) {
			UserStkNotifMapping usnMap = new UserStkNotifMapping(userId, stkId, AppConstants.INVALID_DEFAULT_NOTIFICATION_ID, 
					AppConstants.SUBSCR_STAT_Y, AppConstants.INVALID_DEFAULT_NOTIFICATION_FACTOR, stock.getCurrentStockPrice());
			userStkNotifMappingRepository.save(usnMap);
			double costBasis = stkCount*stock.getCurrentStockPrice();
			Holdings holding = new Holdings(userId, stkId, stkCount, stock.getCurrentStockPrice(), stock.getCurrentStockPrice(), 
											AppConstants.DEFAULT_CHANGE_PERCENTAGE, AppConstants.DEFAULT_TOTAL_GL_PERCENTAGE,costBasis);
			holdingsService.createHolding(holding);			
		} else {
			List<Holdings> listHolding = holdingsService.getHoldingForUsrIdStkId(userId,stkId);
			for(Holdings hold : listHolding) {
//				if(hold.getAvgStockPrice() != stock.getCurrentStockPrice()) {
					hold = holdingsUtil.calcChangeGLpercentage(stkCount, stock, hold);
					
					holdingsService.removeHolding(hold.getHoldingsid());
	
					holdingsService.createHolding(hold);	
//				}
			}
		}
	}
	

	private List<UserStkNotifMapping> updateUSNmappings(int userId, List<Integer> stkIds, List<UserStkNotifMapping> listMappings) {
		if(!stkIds.isEmpty()) {
			List<Stock> stocks = stockService.getStocksIn(stkIds);
			Map<Long, Stock> stocksMap = stocks.stream()
				      .collect(Collectors.toMap(Stock::getStockId, stock ->(Stock) stock));
			for(Integer id : stkIds) {
				Stock stock = stocksMap.get((long)id);
				UserStkNotifMapping usnMapping = new UserStkNotifMapping(
						userId, id, AppConstants.INVALID_DEFAULT_NOTIFICATION_ID, 
						AppConstants.SUBSCR_STAT_Y, AppConstants.INVALID_DEFAULT_NOTIFICATION_FACTOR, 
						stock.getCurrentStockPrice());
				listMappings.add(usnMapping);
			}
		}
		return listMappings;
	}

	private List<List<Integer>> identifyAlreadyAddedStocks(int userId, List<Integer> stkIds) {
		List<List<Integer>> listOfStkIds = new ArrayList<List<Integer>>();
		List<Integer> newStks = new ArrayList<Integer>();
		List<Integer> existingStks = new ArrayList<Integer>();
		List<UserStkNotifMapping> existingStocks = userStkNotifMappingRepository.findByUserId(userId);
		if(!existingStocks.isEmpty()) {
			Set<Long> setExistingStkIds = existingStocks.stream().map(x->x.getStkId()).collect(Collectors.toSet());
			for(int i=0; i<stkIds.size();i++) {
				if(!setExistingStkIds.contains((long) stkIds.get(i))) {
					existingStks.add(stkIds.get(i));
				} else {
					newStks.add(stkIds.get(i));
				}
			}
		} else {
			listOfStkIds.add(new ArrayList<Integer>());
			listOfStkIds.add(stkIds);
		}
		return listOfStkIds;
	}

	@Override
	public void subscribeToNotification(int userId, int notificationId, 
					char notificationSubscrStatus, int notificationFactor) {
		List<UserStkNotifMapping> listMappings = userStkNotifMappingRepository.findByUserId(userId);
		listMappings.forEach(record -> record.setNotifId(notificationId));
		listMappings.forEach(record -> record.setSubscriptionStatus(notificationSubscrStatus));
		listMappings.forEach(record -> record.setSubscrNotifFactor(notificationFactor));
		listMappings.forEach(record -> record.setUpdatedNotifFactor(notificationFactor));
		if(!listMappings.isEmpty()) {
			userStkNotifMappingRepository.saveAll(listMappings);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.intuit.stockservice.service.UserStockNotifService#getAllMappings()
	 * 
	 * @return only mappings that are subscribed
	 */
	@Override
	public List<UserStkNotifMapping> getAllSubscribedMappings() {
		List<UserStkNotifMapping> listOfMappings = userStkNotifMappingRepository.findBySubscriptionStatus(AppConstants.SUBSCR_STAT_Y);
		return listOfMappings;
	}

	@Override
	public List<UserStkNotifMapping> getAllNotificationForUser(int userId) {
		List<UserStkNotifMapping> listOfMappings = userStkNotifMappingRepository.findByUserId(userId);
		return listOfMappings;
	}

	@Override
	public void updateUSNmappings(List<UserStkNotifMapping> usnMappings) {
		if(!usnMappings.isEmpty()) {
			userStkNotifMappingRepository.saveAll(usnMappings);
		}
	}
}