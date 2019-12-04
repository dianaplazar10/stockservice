package com.stocks.stockservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.stocks.stockservice.model.UserStkNotifMapping;

@Service
public interface UserStockNotifService {
	
	void addStocks(int userId, List<Integer> stkIds);
	void subscribeToNotification(int userId, int notificationId,
			char notificationSubscrStatus,
			int notificationFactor);
	List<UserStkNotifMapping> getAllSubscribedMappings();
	
	List<UserStkNotifMapping> getAllNotificationForUser(int userId);
	
	void updateUSNmappings(List<UserStkNotifMapping> usnMappings);

}
