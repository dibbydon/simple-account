package com.simple.account.model;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

public class AccountTransfer {
	@NotNull
    private Long accountNumberToDebit;
	@NotNull
    private Long accountNumberToCredit;
	@NotNull
    private BigDecimal amount;
	@NotNull
    private String reference;
    
    public AccountTransfer(){
    	this.accountNumberToDebit = 0L;
    	this.accountNumberToCredit = 0L;
    	this.amount = BigDecimal.ZERO;
    	this.reference = "";
    }
    
    public AccountTransfer(Long accountNumberToDebit, Long AccountNumberToCredit, BigDecimal amount, String reference) {
    	this.accountNumberToDebit = accountNumberToDebit;
    	this.accountNumberToCredit = AccountNumberToCredit;
    	this.amount = amount;
    	this.reference = reference;
    }
    
	public Long getAccountNumberToDebit() {
		return accountNumberToDebit;
	}
	public Long getAccountNumberToCredit() {
		return accountNumberToCredit;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	
	public String getReference() {
		return reference;
	}
}
