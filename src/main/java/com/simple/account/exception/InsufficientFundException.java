package com.simple.account.exception;

public class InsufficientFundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2380923752972000858L;
	
	public InsufficientFundException(String message) {
		super(message);
	}

}
