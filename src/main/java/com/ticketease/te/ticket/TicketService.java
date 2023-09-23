package com.ticketease.te.ticket;

import org.springframework.stereotype.Service;

import com.ticketease.te.performance.Performance;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TicketService {
	private final TicketRepository ticketRepository;

	public GradeCount countTicketByGradeForPerformance(Performance performance) {
		return GradeCount.from(ticketRepository.findAllById(performance.getTicketIds()));
	}

	public Ticket findTicketById(Long ticketId) {
		return ticketRepository.findById(ticketId)
			.orElseThrow(() -> new RuntimeException("존재하지 않는 상품입니다 상품Id : " + ticketId));
	}

	public void saveTicket(Ticket ticket) {
		ticketRepository.save(ticket);
	}
}
