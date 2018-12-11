package com.simple.account.exception;

public class ResourceNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3761260312942726645L;
	
	public ResourceNotFoundException(String message){
		super(message);
	}

}
