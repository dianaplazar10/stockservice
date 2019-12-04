package com.stocks.stockservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.stocks.stockservice.model.Stock;

	@Service
	public interface StockService {
		Stock createStock(Stock stock);
		Stock getStock(long stockId);
		List<Stock> getAll();
		List<Stock> getStocksIn(List<Integer> stockIds);
	}
