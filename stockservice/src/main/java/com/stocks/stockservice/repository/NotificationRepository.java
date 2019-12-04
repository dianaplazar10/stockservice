package com.stocks.stockservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stocks.stockservice.model.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long>{
}
