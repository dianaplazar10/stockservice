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
@Table(name="notification")
public class Notification implements Serializable {

	private static final long serialVersionUID = 3280132012952274918L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@NotNull
	@Column(name="notificationid")
	long notificationId;
	
	@NotNull
	@Column(name="notificationname")
	String notificationName;
	
	@NotNull
	@Column(name="notificationfactor")
	int notificationFactor; 
	
	@CreationTimestamp	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createDateTime")
	private Date createDateTime;
 
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updateDateTime")
    private Date updateDateTime;
	
	public Notification() {
	}
	
	public Notification(long notificationId, String notificationName, int notificationFactor) {
		this.notificationId = notificationId;
		this.notificationName = notificationName;
		this.notificationFactor = notificationFactor;
	}

	public long getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(long notificationId) {
		this.notificationId = notificationId;
	}

	public String getNotificationName() {
		return notificationName;
	}

	public void setNotificationName(String notificationName) {
		this.notificationName = notificationName;
	}

	public int getNotificationFactor() {
		return notificationFactor;
	}

	public void setNotificationFactor(int notificationFactor) {
		this.notificationFactor = notificationFactor;
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

}
