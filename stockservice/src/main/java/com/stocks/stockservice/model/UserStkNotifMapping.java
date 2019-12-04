package com.stocks.stockservice.model;

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

import com.stocks.stockservice.dto.AppConstants;

@Entity
@Table(name="userStkNotifMapping")
public class UserStkNotifMapping {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "usnmappingid")
	private long usnId;
	
	@NotNull
	@Column(name = "userid")
	private long userId;
	
	@NotNull
	@Column(name = "stockid")
	private long stkId;

	@Column(name = "notificationid")
	private long notifId;
	
	@Column(name = "subscriptionStatus")
	private char subscriptionStatus;
	
	@Column(name = "notifystatus")
	private char notifyStatus = AppConstants.NOTIF_STAT_N;
	
	@Column(name = "updNotificationFactor")
	private int updatedNotifFactor;
	
	@Column(name = "subscribedNotificationFactor")
	private int subscrNotifFactor;
	
	public int getSubscrNotifFactor() {
		return subscrNotifFactor;
	}

	public void setSubscrNotifFactor(int subscrNotifFactor) {
		this.subscrNotifFactor = subscrNotifFactor;
	}

	@Column(name = "baseStockPrice")
	private double baseStkPrice;
	
	@CreationTimestamp	
	@Column(name = "createDateTime")
    private LocalDateTime createDateTime;
 
    @UpdateTimestamp
	@Column(name = "updateDateTime")
    private LocalDateTime updateDateTime;
    
    public UserStkNotifMapping() {
	}
    
    public UserStkNotifMapping(long userId, long stkId, long notifId, char subscriptionStatus, int updatedNotifFactor, double baseStkPrice) {
    	this.userId = userId;
    	this.stkId = stkId;
    	this.notifId = notifId;
    	this.subscriptionStatus = subscriptionStatus;
    	this.updatedNotifFactor = updatedNotifFactor;
    	this.baseStkPrice = baseStkPrice;
    }

	public long getUsnId() {
		return usnId;
	}

	public void setUsnId(long usnId) {
		this.usnId = usnId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getStkId() {
		return stkId;
	}

	public void setStkId(long stkId) {
		this.stkId = stkId;
	}

	public long getNotifId() {
		return notifId;
	}

	public void setNotifId(long notifId) {
		this.notifId = notifId;
	}

	public char getSubscriptionStatus() {
		return subscriptionStatus;
	}

	public void setSubscriptionStatus(char subscriptionStatus) {
		this.subscriptionStatus = subscriptionStatus;
	}

	public int getUpdatedNotifFactor() {
		return updatedNotifFactor;
	}

	public void setUpdatedNotifFactor(int updatedNotifFactor) {
		this.updatedNotifFactor = updatedNotifFactor;
	}

	public double getBaseStkPrice() {
		return baseStkPrice;
	}

	public void setBaseStkPrice(double baseStkPrice) {
		this.baseStkPrice = baseStkPrice;
	}

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
	
	public char getNotifyStatus() {
		return notifyStatus;
	}

	public void setNotifyStatus(char notifyStatus) {
		this.notifyStatus = notifyStatus;
	}
}
