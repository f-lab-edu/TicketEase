package com.ticketease.te.account;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ticketease.te.exception.ExceptionCode;
import com.ticketease.te.exception.ExceptionHandler;
import com.ticketease.te.member.Member;
import com.ticketease.te.ticket.Grade;
import com.ticketease.te.ticket.Seat;
import com.ticketease.te.ticket.Ticket;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

	@InjectMocks
	private AccountService accountService;

	@Mock
	private AccountRepository accountRepository;

	@Test
	@DisplayName("잔액이 충분할 때 금액 차감 성공")
	void deductAmount_success() {
		Member member = mock(Member.class);
		Account mockAccount = Account.of(100_000L, member);
		Ticket mockTicket = Ticket.of(Seat.of(100, Grade.S), 50_000);

		when(accountRepository.findAccountByMemberNickName(member.getNickName())).thenReturn(Optional.of(mockAccount));
		when(accountRepository.save(mockAccount)).thenReturn(mockAccount);

		Integer requestSeatCount = 1;
		accountService.deductAmount(member.getNickName(), requestSeatCount * mockTicket.getFixedPrice());

		assertEquals(50_000L, mockAccount.getAmount().longValue());
		verify(accountRepository, times(1)).save(any(Account.class));
	}

	@Test
	@DisplayName("잔액이 부족할 때 예외 발생")
	void deductAmount_insufficientBalance_shouldThrowException() {
		Member member = mock(Member.class);
		Account mockAccount = Account.of(100_000L, member);
		Ticket mockTicket = Ticket.of(Seat.of(100, Grade.S), 50_000_000);

		when(accountRepository.findAccountByMemberNickName(member.getNickName())).thenReturn(Optional.of(mockAccount));

		Integer requestSeatCount = 1;
		ExceptionHandler ex = assertThrows(ExceptionHandler.class,
			() -> accountService.deductAmount(member.getNickName(), requestSeatCount * mockTicket.getFixedPrice()));
		assertEquals(ex.getCode(), ExceptionCode.LACK_OF_MONEY);
	}
}

