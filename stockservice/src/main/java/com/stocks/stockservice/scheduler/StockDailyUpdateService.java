package com.stocks.stockservice.scheduler;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.stocks.stockservice.dto.AppConstants;
import com.stocks.stockservice.exceptionhandlers.CustomRuntimeException;
import com.stocks.stockservice.model.Holdings;
import com.stocks.stockservice.model.Stock;
import com.stocks.stockservice.model.UserStkNotifMapping;
import com.stocks.stockservice.service.HoldingsService;
import com.stocks.stockservice.service.StockService;
import com.stocks.stockservice.service.UserStockNotifService;
import com.stocks.stockservice.utils.HoldingsUtil;

@Configuration
@EnableScheduling
@ConditionalOnProperty(name = "scheduling.enabled",matchIfMissing=true)
public class StockDailyUpdateService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(StockDailyUpdateService.class);
     
	private static ZoneId newYokZoneId = ZoneId.of("America/New_York");
	private static LocalDateTime currentDateTime = LocalDateTime.now();    
	private static ZonedDateTime currentETime = currentDateTime.atZone(newYokZoneId);       //ET Time
	
	@Autowired
	private StockService stockService;
	
	@Autowired
	private UserStockNotifService userStockNotifService;
	@Autowired
	private HoldingsService holdingsService;
	
	private HoldingsUtil holdingsUtil = new HoldingsUtil();

    
	/*
	 * refer crontab.guru to understand more about cron paramenters
			//fixedDelayString = "${scheduleInterval.InHour}") //PT1H//fixedDelayString = 2000L 
	 */
	
	@Scheduled(cron = "1 * * * * *", zone = "CET")//1 00 * * *
	void customJob() throws InterruptedException {
		List<Stock> listOfStocks = stockService.getAll();
		Map<Long, Stock> stocksMap = listOfStocks.stream()
			      .collect(Collectors.toMap(Stock::getStockId, stock ->(Stock) stock));
		updateStocksforDailyAveragePrice(stocksMap);
	}

	private void updateStocksforDailyAveragePrice(Map<Long, Stock> stocksMap ) {
		List<UserStkNotifMapping> listOfUSNmapping = userStockNotifService.getAllSubscribedMappings();
		List<UserStkNotifMapping> updatedListOfUSNmapping = new ArrayList<UserStkNotifMapping>();

		for(UserStkNotifMapping usnMap : listOfUSNmapping) {
			Stock stk = stocksMap.get(usnMap.getStkId());
			if(stk.getCurrentStockPrice()!=stk.getStockAvgToday()) {
				usnMap.setUpdatedNotifFactor(usnMap.getUpdatedNotifFactor()-1);
				//if updFactor==0, set 'notify' to 'Y'
				if(usnMap.getUpdatedNotifFactor() == 0) {
					usnMap.setNotifyStatus(AppConstants.NOTIF_STAT_Y);
					usnMap.setUpdatedNotifFactor(usnMap.getSubscrNotifFactor());
				}
			}
			updatedListOfUSNmapping.add(usnMap);
		}
		try {
			userStockNotifService.updateUSNmappings(updatedListOfUSNmapping);
		} catch (Exception ex) {
			LOGGER.info("::: Cron failed to update the stocks today");
			LOGGER.info("Current server time :" + currentDateTime);
			LOGGER.info("Current Local time :" +currentETime);
			throw new CustomRuntimeException("");
		}
		logJobCompletion("::: Cron completed to update new base stock price in the DWH :::");
	}
	
	private void updateStockDayBasePriceInHoldings(Map<Long, Stock> stocksMap ) {
		List<Holdings> holdingsToUpdate = new ArrayList<Holdings>();
		for(Map.Entry<Long, Stock> entry : stocksMap.entrySet()) {
			int stockId = entry.getKey().intValue();
			List<Holdings> listHoldingsForStock = holdingsService.getAllHoldingsForStockId(stockId);
			if(!listHoldingsForStock.isEmpty()) {
				listHoldingsForStock.forEach(holding -> holding.setStkBasePriceToday(entry.getValue().getCurrentStockPrice()));
				//Update Change and GL percentage for each holdings
				for(Holdings holding : listHoldingsForStock) {
					Stock stk = entry.getValue();
					holding = holdingsUtil.calcChangeGLpercentage(0, stk, holding);// No: of stocks =0 to indicate that its n0t a new holding
					if(holding.getAvgStockPrice() != stk.getCurrentStockPrice()) {
						
						holdingsService.removeHolding(holding.getHoldingsid());
	
						holdingsService.createHolding(holding);	
					}
				}
				holdingsToUpdate.addAll(listHoldingsForStock);
			}
		}
		holdingsService.updateHoldings(holdingsToUpdate);
		logJobCompletion("::: Cron completed to update new base stock price in Holdings :::");
	}

	private void logJobCompletion(String logMsg) {
			
		LOGGER.info(logMsg);
		LOGGER.info("Current server time :" + currentDateTime);
		LOGGER.info("Current Local time :" +currentETime);
	}
	
	@Scheduled(cron = "1 * * * * *", zone = "CET")//1 00 * * *
	void holdingsGLupdateJob() throws InterruptedException {
		List<Stock> listOfStocks = stockService.getAll();
		Map<Long, Stock> stocksMap = listOfStocks.stream()
			      .collect(Collectors.toMap(Stock::getStockId, stock ->(Stock) stock));
		updateStockDayBasePriceInHoldings(stocksMap);
	}
	
}