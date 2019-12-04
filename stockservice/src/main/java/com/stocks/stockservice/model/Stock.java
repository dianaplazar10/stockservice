package com.stocks.stockservice.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name="stock")
public class Stock implements Serializable{

	private static final long serialVersionUID = -2499744329812511063L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@NotNull
	@Column(name="stockId")
	long stockId;
	
	@NotNull
	@Column(name="companyname")
	String companyName;
	
	@NotNull
	@Column(name="stockavgtoday")
	double stockAvgToday;
	
	@NotNull
	@Column(name="currentstockprice")
	double currentStockPrice;
	
	@CreationTimestamp	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createDateTime")
	private Date createDateTime;
 
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updateDateTime")
    private Date updateDateTime;

	
	public long getStockId() {
		return stockId;
	}

	public void setStockId(long stockId) {
		this.stockId = stockId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public double getStockAvgToday() {
		return stockAvgToday;
	}

	public void setStockAvgToday(double stockAvgToday) {
		this.stockAvgToday = stockAvgToday;
	}

	public double getCurrentStockPrice() {
		return currentStockPrice;
	}

	public void setCurrentStockPrice(double currentStockPrice) {
		this.currentStockPrice = currentStockPrice;
	}

	public Date getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}

	public Date getUpdateDateTime() {
		return updateDateTime;
	}

	public void setUpdateDateTime(Date updateDateTime) {
		this.updateDateTime = updateDateTime;
	}

	public Stock(String companyName, double stockAvgToday, double  currentStockPrice) {
		this.companyName = companyName;
		this.stockAvgToday = stockAvgToday;
		this.currentStockPrice = currentStockPrice;
	}
	
	public Stock() {
	}

}
