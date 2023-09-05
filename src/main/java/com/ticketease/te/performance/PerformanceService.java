package com.ticketease.te.performance;


import com.ticketease.te.ticket.GradeCount;
import com.ticketease.te.ticket.TicketService;
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