package com.stocks.stockservice;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.stocks.stockservice.controller.StockController;
import com.stocks.stockservice.model.Stock;
import com.stocks.stockservice.repository.StockRepository;
import com.stocks.stockservice.service.StockService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StockServiceTestsMain {
	
	@Mock
	private StockRepository stockRepo;
	
	@Autowired
	private StockService stockService;
	
	@Test
	public void getStocks() {
		Stock stk = getMockStock();
		when(stockRepo.findByCompanyName("Test Company")).thenReturn(stk);
		assertEquals("Test Company",stk.getCompanyName());
		//verify(stockRepo, times(1)).delete(stk);
	}

	private Stock getMockStock() {
		Stock stk = new Stock("Test Company", 22.1, 22.1);
		return stk;
	}

}
