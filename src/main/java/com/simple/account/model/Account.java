package com.simple.account.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


@Entity
@Table(name="ACCOUNTS")
public class Account implements Serializable{
    
	/**
	 * 
	 */
	private static final long serialVersionUID = -1062353490958933960L;
	@Id
	@Column(name="ACCT_NUMBER")
	@NotNull
	private Long accountNumber;
	@Column(name="ACCT_TYPE")
	@NotNull
	private AccountType accountType;
	@Column(name="ACCT_BALANCE")
	@NotNull
	private BigDecimal balance;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="account", cascade=CascadeType.MERGE, orphanRemoval = true)
	@Fetch(FetchMode.JOIN)
	private List<AccountTransaction> transactions = new ArrayList<>();
	
	public Account(){
		
	}
	
	public Account(Long accountNumber, AccountType accountType, BigDecimal balance) {
		this.accountNumber = accountNumber;
		this.accountType = accountType;
		this.balance = balance;
	}

	public Long getAccountNumber() {
		return accountNumber;
	}


	public AccountType getAccountType() {
		return accountType;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void debitAccount(BigDecimal amount){
		this.balance = balance.subtract(amount);
	}
	
	public void creditAccount(BigDecimal amount){
		this.balance = balance.add(amount);
	}
    
	public void addTransactions(AccountTransaction transaction) {
		 this.transactions.add(transaction);
	}

	public List<AccountTransaction> getTransactions() {
		return transactions;
	}
	
	

}
