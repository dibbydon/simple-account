package com.simple.account.model;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.hateoas.ResourceSupport;

public class AccountSummary extends ResourceSupport {
	
	private Long accountNumber;
	private BigDecimal balance;
	private List<AccountTransactionSummary> transactionSummary;
	
	public AccountSummary(Long accountNumber, BigDecimal balance, List<AccountTransactionSummary> transactionSummary) {
		this.accountNumber = accountNumber;
		this.balance = balance;
		this.transactionSummary = transactionSummary;
	}
	
	public Long getAccountNumber() {
		return accountNumber;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public List<AccountTransactionSummary> getTransactionSummary() {
		return transactionSummary;
	}
}
