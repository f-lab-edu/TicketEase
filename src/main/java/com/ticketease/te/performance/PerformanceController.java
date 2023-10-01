package com.ticketease.te.performance;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ticketease.te.ticket.GradeCount;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class PerformanceController {
	private final PerformanceService performanceService;

	@GetMapping("/api/show/{performanceId}/availableSeat")
	public ResponseEntity<GradeCount> countTicketByGradeFor(@RequestParam Long performanceId) {
		GradeCount gradeCount = performanceService.countTicketByGradeForPerformance(performanceId);
		return ResponseEntity.ok(gradeCount);
	}
}
