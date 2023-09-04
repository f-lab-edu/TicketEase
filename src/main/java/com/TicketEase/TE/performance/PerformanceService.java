package com.TicketEase.TE.performance;

import com.TicketEase.TE.Ticket.GradeCount;
import com.TicketEase.TE.Ticket.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PerformanceService {
    private final PerformanceRepository performanceRepository;
    private final TicketService ticketService;

    public GradeCount countTicketByGradeForPerformance(Long performanceId){
        Performance performance = performanceRepository.findById(performanceId)
                .orElseThrow(() -> new IllegalArgumentException("Performance not found with id: " + performanceId));
        return ticketService.countTicketByGradeForPerformance(performance);
    }
}
