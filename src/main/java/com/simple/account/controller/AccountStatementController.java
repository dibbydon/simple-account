package com.simple.account.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.simple.account.exception.ResourceNotFoundException;
import com.simple.account.model.AccountSummary;
import com.simple.account.service.AccountService;

@RestController("/statement")
public class AccountStatementController {
	
	@Autowired
	private AccountService accountService;
	
	@GetMapping(produces="application/json")
	public ResponseEntity<AccountSummary> getStatement(@RequestParam("accountNumber") Long accountNumber) throws ResourceNotFoundException{
		AccountSummary accountSummary = accountService.getStatement(accountNumber);
		return new ResponseEntity<>(accountSummary, HttpStatus.OK);
	}

}
