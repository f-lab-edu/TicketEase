package com.ticketease.te.performance;

import org.springframework.stereotype.Service;

import com.ticketease.te.ticket.GradeCount;
import com.ticketease.te.ticket.TicketDataAccessService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PerformanceService {
	private final PerformanceRepository performanceRepository;
	private final TicketDataAccessService ticketDataAccessService;

	public GradeCount countTicketByGradeForPerformance(Long performanceId) {
		Performance performance = performanceRepository.findById(performanceId)
			.orElseThrow(() -> new IllegalArgumentException("Performance not found with id: " + performanceId));
		return ticketDataAccessService.countTicketByGradeForPerformance(performance);
	}
}
