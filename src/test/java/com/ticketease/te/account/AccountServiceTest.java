package com.ticketease.te.account;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class AccountServiceTest {

	@InjectMocks
	private AccountService accountService;

	@Mock
	private AccountDataAccessService accountDataAccessService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	@DisplayName("잔액이 충분할 때 금액 차감 성공")
	void deductAmount_success() {
		String nickName = "testUser";
		Account mockAccount = Account.of(100_000L, 1L);
		Integer requestSeatCount = 1;
		Integer ticketPrice = 50_000;
		Integer totalPaymentAmount = ticketPrice * requestSeatCount;

		when(accountDataAccessService.findAccountIdByNickName(nickName)).thenReturn(1L);
		when(accountDataAccessService.findAccountByAccountId(1L)).thenReturn(mockAccount);

		accountService.deductAmountOnPayment(nickName, totalPaymentAmount);

		assertEquals(50_000L, mockAccount.getAmount().longValue());
		verify(accountDataAccessService, times(1)).findAccountIdByNickName(nickName);
		verify(accountDataAccessService, times(1)).findAccountByAccountId(1L);
		verify(accountDataAccessService, times(1)).renewAccount(mockAccount);
	}

	@Test
	@DisplayName("잔액이 부족할 때 예외 발생")
	void deductAmount_insufficientBalance_shouldThrowException() {
		String nickName = "testUser";
		Account mockAccount = Account.of(40_000L, 1L);
		Integer requestSeatCount = 1;
		Integer ticketPrice = 50_000;
		Integer totalPaymentAmount = ticketPrice * requestSeatCount;

		when(accountDataAccessService.findAccountIdByNickName(nickName)).thenReturn(1L);
		when(accountDataAccessService.findAccountByAccountId(1L)).thenReturn(mockAccount);

		assertThrows(RuntimeException.class,
			() -> accountService.deductAmountOnPayment(nickName, totalPaymentAmount));

		verify(accountDataAccessService, times(1)).findAccountIdByNickName(nickName);
		verify(accountDataAccessService, times(1)).findAccountByAccountId(1L);
		verify(accountDataAccessService, never()).renewAccount(mockAccount);
	}
}

