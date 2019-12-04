package com.stocks.stockservice.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.stocks.stockservice.exceptionhandlers.CustomRuntimeException;
import com.stocks.stockservice.model.Stock;
import com.stocks.stockservice.service.StockService;

@RestController
public class StockController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(StockController.class);
	
	@Autowired
	private StockService stockService;
	
	@GetMapping(value="/stocks/{stockId}")
	public ResponseEntity<Stock> getStock(@PathVariable("stockId") String stockId) {
		Stock stk = stockService.getStock(Long.parseLong(stockId));
		if(stk==null) {
			return ResponseEntity.noContent().build(); 
		} 
		return new ResponseEntity<Stock>(stk,HttpStatus.OK);
	}
	
	@GetMapping(value="/stocks")
	public ResponseEntity<List<Stock>> getAllStocks() {
		List<Stock> listOfNotifs = stockService.getAll();
		if(listOfNotifs.isEmpty()) {
			return ResponseEntity.noContent().build(); 
		} 
		return new ResponseEntity<List<Stock>>(listOfNotifs,HttpStatus.OK);
	}
		
	@PostMapping(value="/stocks",consumes = {MediaType.APPLICATION_JSON_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Stock> createStock(@Valid @RequestBody Stock stock) {
		if(stock.getCompanyName().isEmpty() || stock.getCompanyName()==null) {
			throw new CustomRuntimeException("A valid Company name attribute is Mandatory to create a stock");
		}
		Stock stk = stockService.createStock(stock);
		return new ResponseEntity<Stock>(stk,HttpStatus.CREATED);
	}


}
