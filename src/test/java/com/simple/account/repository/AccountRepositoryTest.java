package com.simple.account.repository;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.simple.account.model.Account;
import com.simple.account.model.AccountType;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AccountRepositoryTest {
    
	@Autowired
	private AccountRepository repository;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void givenAccountTosPersistShouldRetrieveWithSameValue() {
		Account account = new Account(10000L, AccountType.CA, new BigDecimal(1000));
		entityManager.persistAndFlush(account);
		Optional<Account> savedAccount = repository.findById(10000L);
		assertNotNull(savedAccount);
		assertThat("expected value not returned", savedAccount.get().getAccountNumber(), is(10000L));
		assertEquals("expected value not returned", new BigDecimal(1000), savedAccount.get().getBalance());
		assertThat("expected value not returned", savedAccount.get().getAccountType(), is(AccountType.CA) );
		
	}

}
