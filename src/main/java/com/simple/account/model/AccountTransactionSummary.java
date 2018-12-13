package com.simple.account.model;

import java.math.BigDecimal;
import java.util.Date;

public class AccountTransactionSummary {
	private Long transactionId;
	private Long accountId;
	private String reference;
	private BigDecimal creditAmount;
	private BigDecimal debitAmount;
	private BigDecimal balance;
	private Date createDate;
	
	public AccountTransactionSummary(Long transactionId, Long accountId, String reference, BigDecimal creditAmount, BigDecimal debitAmount, BigDecimal balance, Date date) {
		this.transactionId = transactionId;
		this.accountId = accountId;
		this.reference = reference;
		this.creditAmount = creditAmount;
		this.debitAmount= debitAmount;
		this.balance = balance;
		this.createDate = date;
	}
	
	public Long getTransactionId() {
		return transactionId;
	}
	public Long getAccountId() {
		return accountId;
	}
	public String getReference() {
		return reference;
	}
	public BigDecimal getCreditAmount() {
		return creditAmount;
	}
	public BigDecimal getDebitAmount() {
		return debitAmount;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public Date getCreateDate() {
		return createDate;
	}
}
