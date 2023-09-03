package com.TicketEase.TE.Ticket;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TicketService{
    private TicketRepository ticketRepository;

    public GradeCount countTicketByGradeForPerformance(Long performanceId) {
        List<Ticket> tickets = ticketRepository.findByPerformanceId(performanceId);
        return GradeCount.from(tickets);
    }
}
