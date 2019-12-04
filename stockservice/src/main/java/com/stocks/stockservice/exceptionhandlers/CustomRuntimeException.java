package com.stocks.stockservice.exceptionhandlers;

public class CustomRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 7466769274565672380L;

	public CustomRuntimeException(String message) {
		super(message);	
	}
}
