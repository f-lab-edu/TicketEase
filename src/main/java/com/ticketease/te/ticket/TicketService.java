package com.ticketease.te.ticket;

import org.springframework.stereotype.Service;

import com.ticketease.te.exception.ExceptionCode;
import com.ticketease.te.exception.ExceptionHandler;
import com.ticketease.te.performance.Performance;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TicketService {

	private final TicketRepository ticketRepository;

	public GradeCount countTicketByGradeForPerformance(Performance performance) {
		return GradeCount.from(ticketRepository.findAllById(performance.getTicketIds()));
	}

	public Ticket ReserveSeat(Long ticketId, Integer count) {
		Ticket ticket = ticketRepository.findById(ticketId)
			.orElseThrow(() -> new ExceptionHandler(ExceptionCode.TICKET_NOT_FOUND,
				ExceptionCode.TICKET_NOT_FOUND.getDescription()));
		ticket.getSeat().reserveSeats(count);
		return ticket;
	}
}
