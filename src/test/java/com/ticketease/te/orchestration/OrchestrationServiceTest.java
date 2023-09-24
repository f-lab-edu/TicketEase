package com.ticketease.te.orchestration;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ticketease.te.account.AccountService;
import com.ticketease.te.exception.ExceptionCode;
import com.ticketease.te.exception.ExceptionHandler;
import com.ticketease.te.member.Member;
import com.ticketease.te.memberticket.MemberTicket;
import com.ticketease.te.memberticket.MemberTicketService;
import com.ticketease.te.ticket.Ticket;
import com.ticketease.te.ticket.TicketService;

@ExtendWith(MockitoExtension.class)
class OrchestrationServiceTest {

	@InjectMocks
	private OrchestrationService orchestrationService;

	@Mock
	private TicketService ticketService;
	@Mock
	private AccountService accountService;
	@Mock
	private MemberTicketService memberTicketService;

	@Test
	@DisplayName("티켓서비스 오류발생 시 티켓구매에 실패하다")
	void givenPurchaseTicketRequest_whenTicketServiceError_thenFail() throws Exception {
		//given

		//when
		when(ticketService.ReserveSeat(anyLong(), anyInt())).thenThrow(
			new ExceptionHandler(ExceptionCode.TICKET_NOT_FOUND, ExceptionCode.TICKET_NOT_FOUND.getDescription()));

		//then
		assertThrows(ExceptionHandler.class, () -> orchestrationService.PurchaseTicket("hello", 1L, 10));
		verify(ticketService, times(1)).ReserveSeat(anyLong(), anyInt());
		verify(accountService, never()).deductAmount(anyString(), anyInt());
		verify(memberTicketService, never()).registerTicketForMember(anyLong(), anyLong(), anyInt(), anyInt());

	}

	@Test
	@DisplayName("계좌서비스 오류발생 시 티켓구매에 실패하다")
	void givenPurchaseTicketRequest_whenAccountServiceError_thenFail() throws Exception {
		//given
		Ticket mockTicket = mock(Ticket.class);

		//when
		when(ticketService.ReserveSeat(anyLong(), anyInt())).thenReturn(mockTicket);
		when(accountService.deductAmount(anyString(), anyInt())).thenThrow(
			new ExceptionHandler(ExceptionCode.ACCOUNT_NOT_FOUND, ExceptionCode.ACCOUNT_NOT_FOUND.getDescription()));

		//then
		assertThrows(ExceptionHandler.class, () -> orchestrationService.PurchaseTicket("hello", 1L, 10));
		verify(ticketService, times(1)).ReserveSeat(anyLong(), anyInt());
		verify(accountService, times(1)).deductAmount(anyString(), anyInt());
		verify(memberTicketService, never()).registerTicketForMember(anyLong(), anyLong(), anyInt(), anyInt());

	}

	@Test
	@DisplayName("멤버티켓서비스 오류발생 시 티켓구매에 실패하다")
	void givenPurchaseTicketRequest_whenMemberTicketServiceError_thenFail() throws Exception {
		//given
		Ticket mockTicket = mock(Ticket.class);
		Member mockMember = Member.of("helloWorld", "1234qwer");
		Integer requestSeatCount = 10;

		//when
		when(ticketService.ReserveSeat(anyLong(), anyInt())).thenReturn(mockTicket);
		when(accountService.deductAmount(mockMember.getNickName(), mockTicket.getFixedPrice())).thenReturn(
			mockMember.getId());
		when(memberTicketService.registerTicketForMember(mockMember.getId(), mockTicket.getId(),
			mockTicket.getFixedPrice(),
			requestSeatCount)).thenThrow(
			new ExceptionHandler(ExceptionCode.ACCOUNT_NOT_FOUND, ExceptionCode.ACCOUNT_NOT_FOUND.getDescription()));

		//then
		assertThrows(ExceptionHandler.class,
			() -> orchestrationService.PurchaseTicket(mockMember.getNickName(), mockTicket.getId(), requestSeatCount));
		verify(ticketService, times(1)).ReserveSeat(anyLong(), anyInt());
		verify(accountService, times(1)).deductAmount(anyString(), anyInt());
		verify(memberTicketService, times(1)).registerTicketForMember(mockMember.getId(), mockTicket.getId(),
			mockTicket.getFixedPrice(), requestSeatCount);

	}

	@Test
	@DisplayName("티켓구매에 성공하다")
	void givenPurchaseTicketRequest_whenValid_thenSuccess() throws Exception {
		//given
		Ticket mockTicket = mock(Ticket.class);
		Member mockMember = Member.of("helloWorld", "1234qwer");
		Integer requestSeatCount = 10;

		//when
		when(ticketService.ReserveSeat(anyLong(), anyInt())).thenReturn(mockTicket);
		when(accountService.deductAmount(mockMember.getNickName(), mockTicket.getFixedPrice())).thenReturn(
			mockMember.getId());
		when(memberTicketService.registerTicketForMember(mockMember.getId(), mockTicket.getId(),
			mockTicket.getFixedPrice(), requestSeatCount)).thenReturn(mock(MemberTicket.class));

		//then
		assertDoesNotThrow(
			() -> orchestrationService.PurchaseTicket(mockMember.getNickName(), mockTicket.getId(), requestSeatCount));
		verify(ticketService, times(1)).ReserveSeat(anyLong(), anyInt());
		verify(accountService, times(1)).deductAmount(anyString(), anyInt());
		verify(memberTicketService, times(1)).registerTicketForMember(mockMember.getId(), mockTicket.getId(),
			mockTicket.getFixedPrice(), requestSeatCount);

	}
}
