package com.ticketease.te.ticket;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TicketService {
	private final TicketDataAccessService ticketDataAccessService;

	public void deductSeatsAfterPayment(Long ticketId, Integer requestSeatCount) {
		Seat seat = ticketDataAccessService.getSeat(ticketId);
		seat.reserveSeat(requestSeatCount);
		ticketDataAccessService.saveTicket(ticketId);
	}

	public Integer calculateTicketPrice(Long ticketId, Integer requestSeatCount) {
		Ticket ticket = ticketDataAccessService.findTicketById(ticketId);
		return ticket.getFixedPrice() * requestSeatCount;
	}
}
