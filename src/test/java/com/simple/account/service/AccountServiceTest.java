package com.simple.account.service;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import static org.mockito.BDDMockito.*;import java.math.BigDecimal;
import java.util.Optional;
import static com.simple.account.constant.TransactionCode.*;

import com.simple.account.exception.InsufficientFundException;
import com.simple.account.exception.ResourceNotFoundException;
import com.simple.account.model.Account;
import com.simple.account.model.AccountSummary;
import com.simple.account.model.AccountTransfer;
import com.simple.account.model.AccountType;
import com.simple.account.repository.AccountRepository;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {
	
	
   
	@InjectMocks
	private AccountService accountService;
	
	@Mock
	private AccountRepository repository;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void GivenTransferRequestShouldPerformandReturnAccount() throws ResourceNotFoundException {
		AccountTransfer accountTransfer = new AccountTransfer(10000L, 20000L, new BigDecimal(220), INTERNAL_TRANS);
		Account accountToDebit = new Account(10000L, AccountType.SA, new BigDecimal(1000));
		Account accountToCredit = new Account(20000L, AccountType.SA, new BigDecimal(800));
		
		given(repository.findById(10000L)).willReturn(Optional.ofNullable(accountToDebit));
		given(repository.findById(20000L)).willReturn(Optional.ofNullable(accountToCredit));
		given(repository.saveAndFlush(any())).willReturn(accountToDebit);
		
		AccountSummary accountSummary = accountService.transfer(accountTransfer);
		
		assertEquals("expected value not returned",accountSummary.getBalance(), new BigDecimal(780));
		assertEquals("expected value not returned",accountToDebit.getBalance(), new BigDecimal(780));
		assertEquals("expected value not returned",accountToCredit.getBalance(), new BigDecimal(1020));
		assertEquals("expected value not returned",accountToDebit.getTransactions().size(), 1);
		
	}
	
	@Test(expected=ResourceNotFoundException.class)
	public final void GivenTransferRequestWithIncorrectAccountNumberShouldThrowException() throws ResourceNotFoundException {
		AccountTransfer accountTransfer = new AccountTransfer(10000L, 20000L, new BigDecimal(220), INTERNAL_TRANS);
		Account accountToDebit = new Account(10000L, AccountType.SA, new BigDecimal(1000));
		Account accountToCredit = null;
		
		given(repository.findById(10000L)).willReturn(Optional.ofNullable(accountToDebit));
		given(repository.findById(20000L)).willReturn(Optional.ofNullable(accountToCredit));
		
		accountService.transfer(accountTransfer);
	}
	
	@Test(expected=InsufficientFundException.class)
	public final void GivenTransferRequestWithAmountExceedingBalanceShouldThrowException() throws ResourceNotFoundException {
		AccountTransfer accountTransfer = new AccountTransfer(10000L, 20000L, new BigDecimal(1220), INTERNAL_TRANS);
		Account accountToDebit = new Account(10000L, AccountType.SA, new BigDecimal(1000));
		Account accountToCredit = new Account(20000L, AccountType.SA, new BigDecimal(800));
		
		given(repository.findById(10000L)).willReturn(Optional.ofNullable(accountToDebit));
		given(repository.findById(20000L)).willReturn(Optional.ofNullable(accountToCredit));
		
		accountService.transfer(accountTransfer);
	}
}
