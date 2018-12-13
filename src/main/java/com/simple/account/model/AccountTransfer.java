package com.simple.account.model;

import java.math.BigDecimal;

public class AccountTransfer {
    private Long accountNumberToDebit;
    private Long accountNumberToCredit;
    private BigDecimal amount;
    private String reference;
    
    public AccountTransfer(){
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
