package com.stocks.stockservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.stocks.stockservice.exceptionhandlers.CustomRuntimeException;
import com.stocks.stockservice.model.Holdings;
import com.stocks.stockservice.model.StockServiceUser;
import com.stocks.stockservice.service.HoldingsService;
import com.stocks.stockservice.service.UserService;

@RestController
public class HoldingsController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private HoldingsService holdingsService;
	
	@GetMapping(value="/holdings/{userId}",consumes = {MediaType.APPLICATION_JSON_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<List<Holdings>> getHoldingsForUser(@PathVariable("userId") String userId) {
		StockServiceUser user = userService.getUser(Integer.valueOf(userId));
		if(user == null) {
			throw new CustomRuntimeException("Invalud User with given is : " + userId);
		}
		List<Holdings> listHoldings = holdingsService.getHoldingsForUser(Integer.valueOf(userId));
		if(listHoldings.isEmpty()) {
			return ResponseEntity.noContent().build(); 
		} 
		return new ResponseEntity<List<Holdings>>(listHoldings,HttpStatus.OK);
	}

}
