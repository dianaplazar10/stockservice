package com.stocks.stockservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.stocks.stockservice.model.Notification;

@Service
public interface NotificationService {
	
	Notification createNotif(Notification notification);
	Notification getNotification(long notificationId);
	List<Notification> getAll();

}
