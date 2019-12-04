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
import com.stocks.stockservice.model.Stock;
import com.stocks.stockservice.model.UserStkNotifMapping;
import com.stocks.stockservice.service.StockService;
import com.stocks.stockservice.service.UserStockNotifService;

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
    
	/*
	 * refer crontab.guru to understand more about cron paramenters
			//fixedDelayString = "${scheduleInterval.InHour}") //PT1H//fixedDelayString = 2000L 
	 */
	
	@Scheduled(cron = "1 * * * * *", zone = "CET")//1 00 * * *
	void customJob() throws InterruptedException {
		updateStocksforDailyAveragePrice();
	}

	private void updateStocksforDailyAveragePrice() {
		List<Stock> listOfStocks = stockService.getAll();
		Map<Long, Stock> stocksMap = listOfStocks.stream()
			      .collect(Collectors.toMap(Stock::getStockId, stock ->(Stock) stock));
		
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
		logJobCompletion();
	}

	private void logJobCompletion() {
			
		LOGGER.info("::: Cron completed to update new base stock price in the DWH :::");
		LOGGER.info("Current server time :" + currentDateTime);
		LOGGER.info("Current Local time :" +currentETime);
	}
	
}