package com.ticketease.te.ticket;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TicketServiceTest {

	@Mock
	private TicketDataAccessService ticketDataAccessService;

	@InjectMocks
	private TicketService ticketService;

	@DisplayName("티켓 총액 계산을 성공한다")
	@Test
	void testCalculateTicketPrice() {
		Long ticketId = 1L;
		Integer requestSeatCount = 3;
		Seat seat = Seat.of(10, Grade.S);
		Ticket ticket = Ticket.of(seat, 100);

		when(ticketDataAccessService.findTicketById(ticketId)).thenReturn(ticket);

		Integer totalAmount = ticketService.calculateTicketPrice(ticketId, requestSeatCount);

		assertEquals(300, totalAmount);
	}
}
