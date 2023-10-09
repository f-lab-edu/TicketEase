package com.ticketease.te.performance;

import org.springframework.stereotype.Service;

import com.ticketease.te.ticket.GradeCount;
import com.ticketease.te.ticket.TicketReader;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TicketCountFacadeService {
	private final PerformanceReader performanceReader;
	private final TicketReader ticketReader;

	public GradeCount countTicketByGradeForPerformance(Long performanceId) {
		Performance performance = performanceReader.findBy(performanceId);
		return ticketReader.countTicketByGradeFor(performance);
	}
}
