package com.stocks.stockservice.dto;

import java.util.ArrayList;
import java.util.List;

public class UserStkNotifRequestDto {
	
	private int userId;
	private List<Integer> stockIds = new ArrayList<Integer>();
	int notifId;
	char notifSubsStatus;
	int notificationFactor;
	
	public int getNotificationFactor() {
		return notificationFactor;
	}
	public void setNotificationFactor(int notificationFactor) {
		this.notificationFactor = notificationFactor;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public List<Integer> getStockIds() {
		return stockIds;
	}
	public void setStockIds(List<Integer> stockIds) {
		this.stockIds = stockIds;
	}
	public int getNotifId() {
		return notifId;
	}
	public void setNotifId(int notifId) {
		this.notifId = notifId;
	}
	public char getNotifSubsStatus() {
		return notifSubsStatus;
	}
	public void setNotifSubsStatus(char notifSubsStatus) {
		this.notifSubsStatus = notifSubsStatus;
	}
	
	public UserStkNotifRequestDto() {
	}
	
	public UserStkNotifRequestDto(int userId, List<Integer> stkIds) {
		this.userId = userId;
		this.stockIds = stkIds;
		this.notifSubsStatus = AppConstants.SUBSCR_STAT_N;
	}
	
	public UserStkNotifRequestDto(int userId, List<Integer> stkIds, char notif ) {
		this.userId = userId;
		this.stockIds = stkIds;
		this.notifSubsStatus = AppConstants.SUBSCR_STAT_N;
	}
}
