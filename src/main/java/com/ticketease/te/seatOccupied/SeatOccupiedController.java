package com.ticketease.te.seatOccupied;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ticketease.te.ticket.GradeCount;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class SeatOccupiedController {
	private final SeatOccupiedService seatOccupiedService;

	@PostMapping("/api/SeatOccupied")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void occupiedSeat(String ticketId, Long userId, int requestCount) {
		seatOccupiedService.occupiedSeat(ticketId, userId, requestCount);
	}

	@GetMapping("/apiV2/shows/{performanceId}/availableSeat")
	public ResponseEntity<GradeCount> getAvailableSeats(Long performanceId) {
		return ResponseEntity.ok().body(seatOccupiedService.getAvailableSeats(performanceId));
	}

}

