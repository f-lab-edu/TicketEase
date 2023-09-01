package com.TicketEase.TE.Ticket;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.TicketEase.TE.performance.PerformanceRepository;
import org.springframework.stereotype.Service;

@Service
public class TicketService{
    private TicketRepository ticketRepository;
    private PerformanceRepository performanceRepository;

    public TicketService(final TicketRepository ticketRepository,
                         final PerformanceRepository performanceRepository) {
        this.ticketRepository = ticketRepository;
        this.performanceRepository = performanceRepository;
    }

    public GradeCount countTicketByGradeForPerformance(Long performanceId) {
        List<Ticket> tickets = ticketRepository.findByPerformanceId(performanceId);
        Map<Grade, Integer> result = tickets.stream()
                .collect(Collectors.groupingBy(
                        Ticket::getGrade,
                        ()-> new EnumMap<Grade, Integer>(Grade.class),
                        Collectors.summingInt(Ticket::getSeatCount)
                ));
        return new GradeCount(result);
    }
}
