package com.stocks.stockservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stocks.stockservice.model.Holdings;

@Repository
public interface HoldingsRepository extends JpaRepository<Holdings, Long>{
	
	List<Holdings> findByStkId(int stkId);
	
	List<Holdings> findByUserId(int userId);

	List<Holdings> findByUserIdAndStkId(int userId, int stkId);
	
}
