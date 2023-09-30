package com.ticketease.te.purchase;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ticketease.te.Purchase.PurchaseFacadeService;
import com.ticketease.te.account.AccountService;
import com.ticketease.te.memberticket.MemberTicketService;
import com.ticketease.te.ticket.TicketService;

public class PurchaseFacadeServiceTest {

	@InjectMocks
	private PurchaseFacadeService purchaseFacadeService;

	@Mock
	private TicketService ticketService;

	@Mock
	private AccountService accountService;

	@Mock
	private MemberTicketService memberTicketService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	@DisplayName("티켓 구매 성공")
	void purchaseTicket_success() {
		String nickName = "testUser";
		Long ticketId = 1L;
		Integer requestSeatCount = 2;
		Integer mockTicketPrice = 5000;
		Integer totalPaymentAmount = mockTicketPrice * requestSeatCount;

		when(ticketService.calculateTicketPrice(ticketId, requestSeatCount)).thenReturn(totalPaymentAmount);

		purchaseFacadeService.purchaseTicket(nickName, ticketId, requestSeatCount);

		verify(accountService, times(1)).deductAmountOnPayment(nickName, totalPaymentAmount);

		verify(ticketService, times(1)).deductSeatsAfterPayment(ticketId, requestSeatCount);

		verify(memberTicketService, times(1)).registerTicketForMember(nickName, ticketId, requestSeatCount);
	}
}
