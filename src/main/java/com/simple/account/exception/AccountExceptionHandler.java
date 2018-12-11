package com.simple.account.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.simple.account.model.ErrorDetail;

@ControllerAdvice
public class AccountExceptionHandler {
	
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException rfe){
		ErrorDetail error = new ErrorDetail();
		error.setTitle("Resources Not Found");
		error.setStatus(HttpStatus.NOT_FOUND.value());
		error.setDetail(rfe.getMessage());
		return new ResponseEntity<>(error, null, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(InsufficientFundException.class)
	public ResponseEntity<?> handleInsufficientFundException(InsufficientFundException ife){
		ErrorDetail error = new ErrorDetail();
		error.setTitle("insufficient balance amount");
		error.setStatus(HttpStatus.BAD_REQUEST.value());
		error.setDetail(ife.getMessage());
		return new ResponseEntity<>(error, null, HttpStatus.BAD_REQUEST);
	}

}
