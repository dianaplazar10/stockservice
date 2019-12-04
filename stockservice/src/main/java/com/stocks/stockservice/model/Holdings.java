package com.stocks.stockservice.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name="holdings")
public class Holdings implements Serializable {

	private static final long serialVersionUID = -9162727900634494856L;
	
	public Holdings() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "holdingsid")
	private long holdingsid;
	
	@NotNull
	@Column(name = "userid")
	private int userId;				
	
	@NotNull
	@Column(name = "stkId")
	private int stkId;
	
	@NotNull
	@Column(name = "noOfStocks")
	private int noOfStocks;
	
	@NotNull
	@Column(name = "avgStockPrice")
	private double avgStockPrice;
	
	@NotNull
	@Column(name = "stkBasePriceToday")
	private double stkBasePriceToday;
	
	@NotNull
	@Column(name = "costBasis")
	private double costBasis;
	
	public double getCostBasis() {
		return costBasis;
	}

	public void setCostBasis(double costBasis) {
		this.costBasis = costBasis;
	}

	@NotNull
	@Column(name = "changePercentage")
	private String changePercentage;// (stkBasePriceToday - avgStockPrice)/100
	
	@NotNull
	@Column(name = "glPercent")
	private String glPercent;// ((stkBasePriceToday*noOfStocks) - (avgStockPrice*noOfStocks))/100
	
	public LocalDateTime getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(LocalDateTime createDateTime) {
		this.createDateTime = createDateTime;
	}

	public LocalDateTime getUpdateDateTime() {
		return updateDateTime;
	}

	public void setUpdateDateTime(LocalDateTime updateDateTime) {
		this.updateDateTime = updateDateTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@CreationTimestamp	
	@Column(name = "createDateTime")
    private LocalDateTime createDateTime;
 
    @UpdateTimestamp
	@Column(name = "updateDateTime")
    private LocalDateTime updateDateTime;
	
	public long getHoldingsid() {
		return holdingsid;
	}

	public void setHoldingsid(long holdingsid) {
		this.holdingsid = holdingsid;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public long getStkId() {
		return stkId;
	}

	public void setStkId(int stkId) {
		this.stkId = stkId;
	}

	public int getNoOfStocks() {
		return noOfStocks;
	}

	public void setNoOfStocks(int noOfStocks) {
		this.noOfStocks = noOfStocks;
	}

	public double getAvgStockPrice() {
		return avgStockPrice;
	}

	public void setAvgStockPrice(double avgStockPrice) {
		this.avgStockPrice = avgStockPrice;
	}

	public double getStkBasePriceToday() {
		return stkBasePriceToday;
	}

	public void setStkBasePriceToday(double stkBasePriceToday) {
		this.stkBasePriceToday = stkBasePriceToday;
	}

	public String getChangePercentage() {
		return changePercentage;
	}

	public void setChangePercentage(String changePercentage) {
		this.changePercentage = changePercentage;
	}

	public String getGlPercent() {
		return glPercent;
	}

	public void setGlPercent(String glPercent) {
		this.glPercent = glPercent;
	}
	
	public Holdings(int userId, int stkId, int noOfStocks, double avgStockPrice, 
			double stkBasePriceToday, String changePercentage, String glPercent,double costBasis) {
		this.userId = userId;
		this.stkId = stkId;
		this.noOfStocks = noOfStocks;
		this.avgStockPrice=avgStockPrice;
		this.stkBasePriceToday=stkBasePriceToday;
		this.changePercentage=changePercentage;
		this.glPercent=glPercent;
		this.costBasis=costBasis;
	}

}
