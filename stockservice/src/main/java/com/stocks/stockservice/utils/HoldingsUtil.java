package com.stocks.stockservice.utils;

import com.stocks.stockservice.model.Holdings;
import com.stocks.stockservice.model.Stock;

public class HoldingsUtil {

	public Holdings calcChangeGLpercentage(int stkCount, Stock stock, Holdings holding) {
		Holdings hold = holding;
		if(stkCount!=0) {
			int totalStks = (int) (hold.getNoOfStocks()+stkCount);
			hold.setNoOfStocks(totalStks);
		}
		double avgStockPrice = (hold.getAvgStockPrice() + stock.getCurrentStockPrice())/2;
		hold.setAvgStockPrice(avgStockPrice);
		double change = (stock.getCurrentStockPrice() - avgStockPrice);
		double changePercentage = change/100;
		String updatedChangePercentage = String.valueOf(change) + "  (" + changePercentage + "%)";
		
		hold.setChangePercentage(updatedChangePercentage);
		
		double totalGL = (stock.getCurrentStockPrice()*hold.getNoOfStocks()) - (avgStockPrice*hold.getNoOfStocks());
		double totalGLPercentage = totalGL/100;
		String updatedtotalGLPercentage = String.valueOf(totalGL) + "  (" + totalGLPercentage + "%)";
		
		hold.setGlPercent(updatedtotalGLPercentage);
		double costBasis = hold.getCostBasis() + (stkCount*stock.getCurrentStockPrice());
		hold.setCostBasis(costBasis);
		return hold;
	}
	
}
