package com.simple;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import static com.simple.account.constant.TransactionCode.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import com.simple.account.model.Account;
import com.simple.account.model.AccountTransaction;
import com.simple.account.model.AccountTransfer;
import com.simple.account.model.AccountType;
import com.simple.account.repository.AccountRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SimpleAccountApplicationTests {
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private AccountRepository repository;
	
	private Account payerAccount;
	private Account payeeAccount;
	
	@Before
	public void setUp(){
		
	}
    
	@Test
	public void GivenAnAccountTransferRequestShouldPersistAndConfirmBalances() throws Exception {
		payerAccount = new Account(10000L, AccountType.SA, new BigDecimal(2000));
		payeeAccount = new Account(20000L, AccountType.SA, new BigDecimal(800));
		repository.save(payerAccount);
		repository.save(payeeAccount);
		
		AccountTransfer accountTransfer = new AccountTransfer(10000L, 20000L, new BigDecimal(200), INTERNAL_TRANS);
		ResponseEntity<Account> response = restTemplate.postForEntity("/transfer", accountTransfer, Account.class);
		assertThat(response.getBody().getBalance().setScale(0), is(new BigDecimal(1800)));
		
		Optional<Account> retrievedPayerAccount = repository.findById(10000L);
		List<AccountTransaction> payerTransactions = retrievedPayerAccount.get().getTransactions();
	    
		Optional<Account> retrievedPayeeAccount = repository.findById(20000L);
	    List<AccountTransaction> payeeTransactions = retrievedPayeeAccount.get().getTransactions();
		
		
	    assertThat(retrievedPayerAccount.get().getAccountNumber(), is(10000L));
	    assertThat(retrievedPayerAccount.get().getAccountType(), is(AccountType.SA));
	    assertThat(retrievedPayerAccount.get().getBalance().setScale(0), is(new BigDecimal(1800)));
	    
	    assertThat(payerTransactions.size(), is(1));
	    assertThat(payerTransactions.get(0).getDebitAmount().setScale(0), is(new BigDecimal(200)));
	    assertThat(payerTransactions.get(0).getBalance().setScale(0), is(new BigDecimal(1800)));
	    
	    assertThat(retrievedPayeeAccount.get().getAccountNumber(), is(20000L));
	    assertThat(retrievedPayeeAccount.get().getAccountType(), is(AccountType.SA));
	    assertThat(retrievedPayeeAccount.get().getBalance().setScale(0), is(new BigDecimal(1000)));
	    
	    assertThat(payeeTransactions.size(), is(1));
	    assertThat(payeeTransactions.get(0).getCreditAmount().setScale(0), is(new BigDecimal(200)));
	    assertThat(payeeTransactions.get(0).getBalance().setScale(0), is(new BigDecimal(1000)));
	    
	}

}
