package com.simple.account.controller;

import static com.simple.account.constant.TransactionCode.INTERNAL_TRANS;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.simple.account.model.AccountSummary;
import com.simple.account.model.AccountTransactionSummary;
import com.simple.account.service.AccountService;

@RunWith(SpringRunner.class)
@WebMvcTest(AccountStatementController.class)
public class AccountStatementControllerTest {
    
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private AccountService accountService;
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void givenAnAccountNumberShouldReturntheAccountSummary() throws Exception {
		List<AccountTransactionSummary> transactionSummaryList = new ArrayList<>();
		AccountTransactionSummary transactionSummary = new AccountTransactionSummary(11000L, 10000L, INTERNAL_TRANS, null, new BigDecimal(220), new BigDecimal(780), new Date());
		transactionSummaryList.add(transactionSummary);
		
		given(accountService.getStatement(anyLong())).willReturn(new AccountSummary(10000L, new BigDecimal(780), transactionSummaryList));
		
		mockMvc.perform(get("/statement?accountNumber=10000").accept(MediaType.APPLICATION_JSON))
			   .andExpect(status().isOk())
			   .andExpect(jsonPath("accountNumber", is(10000)))
			   .andExpect(jsonPath("balance", is(780)))
			   .andExpect(jsonPath("transactionSummary[0].transactionId", is(11000)))
			   .andExpect(jsonPath("transactionSummary[0].accountId", is(10000)))
			   .andExpect(jsonPath("transactionSummary[0].reference", is(INTERNAL_TRANS)))
			   .andExpect(jsonPath("transactionSummary[0].debitAmount", is(220)))
			   .andExpect(jsonPath("transactionSummary[0].balance", is(780)))
			   .andReturn();
	}

}
