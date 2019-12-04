package com.stocks.stockservice.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.stocks.stockservice.dto.AppConstants;
import com.stocks.stockservice.dto.UserStkNotifRequestDto;
import com.stocks.stockservice.exceptionhandlers.CustomRuntimeException;
import com.stocks.stockservice.model.Stock;
import com.stocks.stockservice.model.StockServiceUser;
import com.stocks.stockservice.model.UserStkNotifMapping;
import com.stocks.stockservice.service.StockService;
import com.stocks.stockservice.service.UserService;
import com.stocks.stockservice.service.UserStockNotifService;

@RestController
public class UserStkNotifMappingController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserStkNotifMappingController.class);
	
	@Autowired
	private UserStockNotifService userStockNotifService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private StockService stockService;
	
	@PostMapping(value="/addstock")
	public ResponseEntity<Object> addStocksToUser(@RequestBody UserStkNotifRequestDto usnDto) {
		StockServiceUser user = validateUserId(usnDto);
		Stock stk = validateStockId(usnDto);
		if(usnDto.getNoOfStks() == 0) { // can have negative values as well to indicate stocks sold
			throw new CustomRuntimeException("Invalid No of Stocks");
		}
		userStockNotifService.addStockToUser(usnDto.getUserId(), usnDto.getStkId(), usnDto.getNoOfStks());//(usnDto.getUserId(), usnDto.getStockIds());
		return ResponseEntity.ok().build(); 
	}

	private Stock validateStockId(UserStkNotifRequestDto usnDto) {
		if(usnDto.getStkId() == 0) {
			throw new CustomRuntimeException("Invalid Stock Id");
		}
		Stock stock = stockService.getStock(usnDto.getStkId());
		if(stock == null) {
			throw new CustomRuntimeException("Invalid Stock Id");
		}
		return stock;
	}

	private StockServiceUser validateUserId(UserStkNotifRequestDto usnDto) {
		if(usnDto.getUserId() == 0) {
			throw new CustomRuntimeException("Invalid User Id");
		}
		StockServiceUser user = userService.getUser((long) usnDto.getUserId());
		if(user == null) {
			throw new CustomRuntimeException("Invalid User Id");
		}
		return user;
	}
	
	@PostMapping(value="/subscribe")
	public ResponseEntity<Object> subsNotification(@RequestBody UserStkNotifRequestDto usnDto) {
		if(usnDto.getUserId()==0) {
			throw new CustomRuntimeException("Invalid User Id");
		}
		if(usnDto.getNotifId()<=0) {
			throw new CustomRuntimeException("Invalid Notification Id");
		}
		if(!(usnDto.getNotifSubsStatus() == AppConstants.SUBSCR_STAT_N 
				|| usnDto.getNotifSubsStatus() == AppConstants.SUBSCR_STAT_Y)) {
			throw new CustomRuntimeException("Invalid Subscription status value(notifSubsStatus). It should be either 'Y' or 'N'");
		}
		if((usnDto.getNotifSubsStatus() == AppConstants.SUBSCR_STAT_Y)
				&& (usnDto.getNotificationFactor()==0)) {
			throw new CustomRuntimeException("Invalid Notification factor value(notificationFactor) : " 
												+ usnDto.getNotificationFactor());
		}
		userStockNotifService.subscribeToNotification(usnDto.getUserId(), 
								usnDto.getNotifId(), usnDto.getNotifSubsStatus(),usnDto.getNotificationFactor());
		return ResponseEntity.ok().build();
	}
	
	@GetMapping(value="/usnMappings", produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<List<UserStkNotifMapping>> getAllMappings() {
		List<UserStkNotifMapping> listOfUSNmappings = userStockNotifService.getAllSubscribedMappings();
		if(listOfUSNmappings.isEmpty()) {
			return ResponseEntity.noContent().build(); 
		} 
		return new ResponseEntity<List<UserStkNotifMapping>>(listOfUSNmappings,HttpStatus.OK);
	}

}
