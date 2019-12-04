package com.stocks.stockservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.stocks.stockservice.model.Holdings;

@Service
public interface HoldingsService {
	
	Holdings createHolding(Holdings holding);
	List<Holdings> getHoldingsForUser(int userId);
	List<Holdings> getAllHoldingsForStockId(int stkId);
	void updateHoldings(List<Holdings> holdingsToUpdate);
	List<Holdings> getHoldingForUsrIdStkId(int userId, int stkId);
	void removeHolding(long holdingId);

}
