package com.simple.account.service;

import java.math.BigDecimal;
import static com.simple.account.constant.TransactionCode.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simple.account.exception.InsufficientFundException;
import com.simple.account.exception.ResourceNotFoundException;
import com.simple.account.model.Account;
import com.simple.account.model.AccountSummary;
import com.simple.account.model.AccountTransaction;
import com.simple.account.model.AccountTransfer;
import com.simple.account.model.AccountType;
import com.simple.account.model.AccountTransactionSummary;
import com.simple.account.repository.AccountRepository;

@Service
public class AccountService implements InitializingBean{
	
	@Autowired
	private AccountRepository repository;

	@Transactional
	public AccountSummary transfer(AccountTransfer accountTransfer) throws ResourceNotFoundException {
		Account sourceAccount = null;
		
		Long accountNumberToDebit = accountTransfer.getAccountNumberToDebit();
		Long accountNumberToCredit = accountTransfer.getAccountNumberToCredit();
		BigDecimal amount = accountTransfer.getAmount();
		
		Optional<Account> accountToDebit = repository.findById(accountNumberToDebit);
		Optional<Account> accountToCredit = repository.findById(accountNumberToCredit);
		
		if (!accountToDebit.isPresent()) {
			throw new ResourceNotFoundException(String.format("account with account number %s found", accountNumberToDebit));
		} else if (!accountToCredit.isPresent()) {
			throw new ResourceNotFoundException(String.format("account with account number %s found", accountNumberToCredit));
		} else if (amount.compareTo(accountToDebit.get().getBalance()) == 1) {
			throw new InsufficientFundException("insufficient balance amount");
	    } else {
		    accountToDebit.get().debitAccount(amount);
		    AccountTransaction debitTransaction = new AccountTransaction(accountToDebit.get(), accountTransfer.getReference(), null, accountTransfer.getAmount(), accountToDebit.get().getBalance());
		    accountToDebit.get().addTransactions(debitTransaction);
		    
		    accountToCredit.get().creditAccount(amount);
		    AccountTransaction creditTransaction = new AccountTransaction(accountToCredit.get(), accountTransfer.getReference(), accountTransfer.getAmount(), null, accountToCredit.get().getBalance());
		    accountToCredit.get().addTransactions(creditTransaction);
		    
		    sourceAccount = repository.saveAndFlush(accountToDebit.get());
		    repository.saveAndFlush(accountToCredit.get());
		}
		return getAccountSummary(sourceAccount);
	}
	
	
	public AccountSummary getStatement(Long accountNumber) throws ResourceNotFoundException {
		Optional<Account> account = repository.findById(accountNumber);
		if (account.isPresent()) {
			return getAccountSummary(account.get());
		} throw new ResourceNotFoundException("account not found");
	}
	
	
	AccountSummary getAccountSummary(Account account) {
		List<AccountTransactionSummary> tranSummary = new ArrayList<>();
		account.getTransactions().forEach(at -> { AccountTransactionSummary acctTranSummary = new AccountTransactionSummary(at.getTransactionId(), at.getAccount().getAccountNumber(), at.getReference(), at.getCreditAmount(),at.getDebitAmount(), at.getBalance(), at.getCreateDate());
				                                  tranSummary.add(acctTranSummary);
										});
		return new AccountSummary(account.getAccountNumber(), account.getBalance(), tranSummary);
		
	}


	@Override
	public void afterPropertiesSet() throws Exception {
		Account account = new Account(30000L, AccountType.CURRENT, new BigDecimal(1000));
		AccountTransaction transaction2 = new AccountTransaction(account, INTERNAL_TRANS, null, new BigDecimal(200), new BigDecimal(1000));
		AccountTransaction transaction1 = new AccountTransaction(account, INTERNAL_TRANS, new BigDecimal(200), null, new BigDecimal(1200));
		account.addTransactions(transaction2);
		account.addTransactions(transaction1);
		repository.saveAndFlush(account);
	}
	
	


}
