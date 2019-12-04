package com.stocks.stockservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stocks.stockservice.model.StockServiceUser;

@Repository
public interface UserRepository extends JpaRepository<StockServiceUser, Long> {
	
}