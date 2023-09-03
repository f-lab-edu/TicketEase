package com.TicketEase.TE.Ticket;

import com.TicketEase.TE.performance.Performance;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TicketService{
    private final TicketRepository ticketRepository;

    public GradeCount countTicketByGradeForPerformance(Performance performance) {
        List<Long> ticketIds = performance.getTicketIds();
        List<Ticket> tickets = ticketRepository.findAllById(ticketIds);
        return GradeCount.from(tickets);
    }
}
