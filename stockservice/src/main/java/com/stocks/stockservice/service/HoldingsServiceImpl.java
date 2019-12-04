package com.stocks.stockservice.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.stocks.stockservice.model.Holdings;
import com.stocks.stockservice.repository.HoldingsRepository;

@Component
public class HoldingsServiceImpl implements HoldingsService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(HoldingsServiceImpl.class);
	
	@Autowired
	private HoldingsRepository holdingRepository;

	@Override
	public Holdings createHolding(Holdings holding) {
		Holdings holdings = holdingRepository.save(holding);
		return holdings;
	}

	@Override
	public List<Holdings> getHoldingsForUser(int userId) {
		List<Holdings> listOfHoldings = holdingRepository.findByUserId(userId);
		return listOfHoldings;
	}

	@Override
	public List<Holdings> getAllHoldingsForStockId(int stkId) {
		List<Holdings> listOfHoldings = holdingRepository.findByStkId(stkId);
		return listOfHoldings;
	}

	@Override
	public void updateHoldings(List<Holdings> holdingsToUpdate) {
		holdingRepository.saveAll(holdingsToUpdate);
	}

	@Override
	public List<Holdings> getHoldingForUsrIdStkId(int userId, int stkId) {
		List<Holdings> listOfHoldings = holdingRepository.findByUserIdAndStkId(userId,stkId);
		return listOfHoldings;	
	}

	@Override
	public void removeHolding(long holdingId) {
		holdingRepository.deleteById(holdingId);
	}
	
}