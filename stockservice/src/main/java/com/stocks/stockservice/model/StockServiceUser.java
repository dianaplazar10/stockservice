package com.stocks.stockservice.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name="stockserviceuser")
public class StockServiceUser {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@NotNull
	@Column(name = "userid")
	private long userId;
	
	@NotNull
	@Column(name = "username")
	private String userName;
	
	@NotNull
	@Column(name = "pwd")
	private String pwd;
	
	@NotNull
	@Column(name = "email")
	@Email
	private String email;
	
	@NotNull
	@Column(name = "mobilenumber")
	private String mobileNumber;
	
	@NotNull
	@Column(name = "address")
	private String address;
	
	@NotNull
	@Column(name = "ssn")
	private String ssn;
	
	@NotNull
	@Column(name = "bankactno")
	private String bankActNo;
	
	@NotNull
	@Column(name = "bankname")
	private String bankName;
	
	@Column(name = "stocksactbalance")
	private int stocksActBalance;
	
	@NotNull
	@Column(name = "role")
	private String role;//UserRole.SERVICEUSER.name();
	
	@CreationTimestamp	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createDateTime")
	private Date createDateTime;
 
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updateDateTime")
    private Date updateDateTime;

	public StockServiceUser() {
	}
	
	public StockServiceUser(String userName, String email, String mobileNumber, String address, String ssn, 
			String bankActNo, String bankName, int stocksActBalance, String role, String pwd) {
		this.userName = userName;
		this.email = email;
		this.pwd = pwd;
		this.mobileNumber = mobileNumber;
		this.address = address;
		this.ssn = ssn;
		this.bankActNo = bankActNo;
		this.bankName = bankName;
		this.stocksActBalance = stocksActBalance;
		this.role = role;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public String getBankActNo() {
		return bankActNo;
	}

	public void setBankActNo(String bankActNo) {
		this.bankActNo = bankActNo;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public double getStocksActBalance() {
		return stocksActBalance;
	}

	public void setStocksActBalance(int stocksActBalance) {
		this.stocksActBalance = stocksActBalance;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
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
