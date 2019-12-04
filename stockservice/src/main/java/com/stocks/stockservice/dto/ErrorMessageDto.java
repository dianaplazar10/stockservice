package com.stocks.stockservice.dto;

import java.util.Date;

public class ErrorMessageDto {
	
	private Date errorTimestamp;
	private String errorMessage;
	
	public ErrorMessageDto() {
	}
	
	public ErrorMessageDto(Date errorTimestamp, String errorMessage) {
		this.errorTimestamp = errorTimestamp;
		this.errorMessage = errorMessage;
	}
	
	public Date getErrorTimestamp() {
		return errorTimestamp;
	}
	public void setErrorTimestamp(Date errorTimestamp) {
		this.errorTimestamp = errorTimestamp;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
