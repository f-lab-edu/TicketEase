package com.TicketEase.TE.Ticket;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.TicketEase.TE.performance.Performance;
import com.TicketEase.TE.performance.PerformanceDateTime;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class TicketServiceTest {

    @Mock
    TicketRepository ticketRepository;

    @InjectMocks
    TicketService ticketService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("공연 티켓 잔여 개수 조회에 성공한다")
    @Test
    void countTicketByGradeForShow() throws Exception {
        final String name = "레베카";
        final String description = "...";
        PerformanceDateTime performanceDateTime = PerformanceDateTime.of(LocalDateTime.parse("2023-01-01T00:00:00"), LocalDateTime.parse("2023-01-31T01:00:00"));
        Performance performance = Performance.of(name, description, performanceDateTime);

        List<Ticket> tickets = Arrays.asList(
                Ticket.of(10, 100000, Grade.VIP),
                Ticket.of(100, 80000, Grade.R),
                Ticket.of(1000, 50000, Grade.S)
        );

        for (Ticket ticket : tickets) {
            performance.addTicketId(ticket.getId());
        }

        when(ticketRepository.findAllById(performance.getTicketIds())).thenReturn(tickets);

        Map<Grade, Integer> map = new HashMap<>();
        map.put(Grade.VIP, 10);
        map.put(Grade.R, 100);
        map.put(Grade.S, 1000);

        GradeCount expected = GradeCount.of(map);
        GradeCount actual = ticketService.countTicketByGradeForPerformance(performance);

        assertEquals(expected, actual);
    }
}
