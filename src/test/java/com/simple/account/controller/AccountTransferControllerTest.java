package com.simple.account.controller;

import static org.hamcrest.CoreMatchers.is;

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
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static com.simple.account.constant.TransactionCode.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.simple.account.exception.ResourceNotFoundException;
import com.simple.account.model.AccountSummary;
import com.simple.account.model.AccountTransactionSummary;
import com.simple.account.model.AccountTransfer;
import com.simple.account.service.AccountService;

@RunWith(SpringRunner.class)
@WebMvcTest(AccountTransferController.class)
public class AccountTransferControllerTest {
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
	public final void givenAnAmountToTransferBetweenAccountShouldReturnBalance() throws Exception {
		AccountTransfer accountTransfer = new AccountTransfer(10000L, 20000L, new BigDecimal(220), INTERNAL_TRANS);
		
		List<AccountTransactionSummary> transactionSummaryList = new ArrayList<>();
		AccountTransactionSummary transactionSummary = new AccountTransactionSummary(11000L, 10000L, INTERNAL_TRANS, null, new BigDecimal(220), new BigDecimal(780));
		transactionSummaryList.add(transactionSummary);
		
		given(accountService.transfer(any())).willReturn(new AccountSummary(10000L, new BigDecimal(780), transactionSummaryList));
		ObjectMapper mapper = new ObjectMapper();
		
		mockMvc.perform(post("/transfer").accept(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(accountTransfer)).contentType(MediaType.APPLICATION_JSON))
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
	
	@Test
	public final void givenAnAmountToTransferBetweenAccountsWithIncorrectAccountShouldThrowException() throws Exception {
		AccountTransfer accountTransfer = new AccountTransfer(10000L, 20000L, new BigDecimal(220), INTERNAL_TRANS);
		given(accountService.transfer(any())).willThrow(new ResourceNotFoundException(String.format("account with account number %s found", 20000L)));
		ObjectMapper mapper = new ObjectMapper();
		
		mockMvc.perform(post("/transfer").accept(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(accountTransfer)).contentType(MediaType.APPLICATION_JSON))
			   .andExpect(status().isNotFound())
			   .andExpect(jsonPath("title", is("Resources Not Found")))
			   .andExpect(jsonPath("detail", is(String.format("account with account number %s found", 20000L))))
			   .andExpect(jsonPath("status", is(404)))
			   .andReturn();
	}

}
