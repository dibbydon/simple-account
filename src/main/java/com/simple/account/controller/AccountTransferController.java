package com.simple.account.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.simple.account.exception.ResourceNotFoundException;
import com.simple.account.model.AccountSummary;
import com.simple.account.model.AccountTransfer;
import com.simple.account.service.AccountService;

@RestController("/transfer")
public class AccountTransferController {
	
	@Autowired
	private AccountService accountService;
	
	@PostMapping(produces="application/json")
	public ResponseEntity<AccountSummary> transfer(@RequestBody AccountTransfer accountTransfer) throws ResourceNotFoundException{
		AccountSummary accountSummary = accountService.transfer(accountTransfer);
		return new ResponseEntity<>(accountSummary, HttpStatus.OK);
	}
	
}
