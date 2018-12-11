package com.simple.account.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="ACCOUNT_TRANSACTIONS")
public class AccountTransaction implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4080184900568576138L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="TRAN_ID")
	private Long transactionId;
	@ManyToOne
	@JoinColumn(name="ACCT_NUMBER")
	private Account account;
	@Column(name="TRAN_REF")
	private String reference;
	@Column(name="TRAN_CR")
	private BigDecimal creditAmount;
	@Column(name="TRAN_DB")
	private BigDecimal debitAmount;
	@Column(name="TRAN_BAL")
	private BigDecimal balance;
	
	public AccountTransaction() {
		
	}
	
	public AccountTransaction(Account account, String reference, BigDecimal creditAmount, BigDecimal debitAmount, BigDecimal balance) {
		this.account = account;
		this.reference = reference;
		this.creditAmount = creditAmount;
		this.debitAmount= debitAmount;
		this.balance = balance;
	}


	public Long getTransactionId() {
		return transactionId;
	}
	
	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	public Account getAccount() {
		return account;
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

}
