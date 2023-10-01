package com.ticketease.te.performance.board;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ticketease.te.performance.Performance;
import com.ticketease.te.performance.PerformanceDateTime;
import com.ticketease.te.performance.PerformanceReader;
import com.ticketease.te.performance.PerformanceService;
import com.ticketease.te.ticket.Grade;
import com.ticketease.te.ticket.GradeCount;
import com.ticketease.te.ticket.TicketDataAccessService;

public class PerformanceServiceTest {

	@InjectMocks
	private PerformanceService performanceService;

	@Mock
	private PerformanceReader performanceReader;

	@Mock
	private TicketDataAccessService ticketDataAccessService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	@DisplayName("공연별 티켓 등급 수량 카운트 성공")
	void countTicketByGradeForPerformance_success() {
		Long performanceId = 1L;
		Performance mockPerformance = Performance.of("Test Performance", "Test Description",
			PerformanceDateTime.of(LocalDateTime.now(), LocalDateTime.now().plusHours(2)));
		GradeCount mockGradeCount = GradeCount.of(Map.of(Grade.S, 10, Grade.R, 5));

		when(performanceReader.findBy(performanceId)).thenReturn(mockPerformance);

		when(ticketDataAccessService.countTicketByGradeFor(mockPerformance)).thenReturn(mockGradeCount);

		GradeCount result = performanceService.countTicketByGradeForPerformance(performanceId);

		assertEquals(mockGradeCount, result);
	}
}
