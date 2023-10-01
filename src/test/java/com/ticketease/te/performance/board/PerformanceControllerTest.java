package com.ticketease.te.performance.board;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.ticketease.te.performance.PerformanceController;
import com.ticketease.te.performance.PerformanceService;
import com.ticketease.te.ticket.Grade;
import com.ticketease.te.ticket.GradeCount;

public class PerformanceControllerTest {

	private MockMvc mockMvc;

	@InjectMocks
	private PerformanceController performanceController;

	@Mock
	private PerformanceService performanceService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(performanceController).build();
	}

	@Test
	@DisplayName("티켓 조회 성공")
	public void countTicketByGradeFor_success() throws Exception {
		Long performanceId = 1L;

		GradeCount mockGradeCount = new GradeCount(Map.of(Grade.VIP, 10, Grade.R, 5, Grade.S, 7));
		given(performanceService.countTicketByGradeForPerformance(performanceId)).willReturn(mockGradeCount);

		String expectedJsonResponse = "{"
			+ "\"gradeCountMap\":{"
			+ "\"VIP\":10,"
			+ "\"R\":5,"
			+ "\"S\":7"
			+ "}"
			+ "}";

		mockMvc.perform(get("/api/show/{performanceId}/availableSeat", performanceId)
				.param("performanceId", performanceId.toString())
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().json(expectedJsonResponse));
	}
}
