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
import com.stocks.stockservice.model.UserStkNotifMapping;
import com.stocks.stockservice.service.UserStockNotifService;

@RestController
public class UserStkNotifMappingController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserStkNotifMappingController.class);
	
	@Autowired
	private UserStockNotifService userStockNotifService;
	
	@PostMapping(value="/addstocks")
	public ResponseEntity<Object> addStocksToUser(@RequestBody UserStkNotifRequestDto usnDto) {
		if(usnDto.getUserId()==0) {
			throw new CustomRuntimeException("Invalid User Id:" + usnDto.getUserId());
		}
		if(usnDto.getStockIds().isEmpty()) {
			throw new CustomRuntimeException("No Stocks to add to the User account with id :" + usnDto.getUserId());
		}
		userStockNotifService.addStocks(usnDto.getUserId(), usnDto.getStockIds());
		return ResponseEntity.ok().build(); 
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
