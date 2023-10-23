package com.ticketease.te.seatOccupied;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ticketease.te.ticket.GradeCount;

@ExtendWith(MockitoExtension.class)
public class SeatOccupiedControllerTest {
	@InjectMocks
	private SeatOccupiedController seatOccupiedController;

	@Mock
	private SeatOccupiedService seatOccupiedService;

	@Test
	public void OccupiedSeat() {
		String ticketId = "1";
		Long userId = 1L;
		int requestCount = 5;

		doNothing().when(seatOccupiedService).occupiedSeat(ticketId, userId, requestCount);

		seatOccupiedController.occupiedSeat(ticketId, userId, requestCount);

		verify(seatOccupiedService, times(1)).occupiedSeat(ticketId, userId, requestCount);
	}

	@Test
	public void GetAvailableSeats() {
		Long performanceId = 1L;
		GradeCount gradeCountMock = mock(GradeCount.class);

		when(seatOccupiedService.getAvailableSeats(performanceId)).thenReturn(gradeCountMock);

		ResponseEntity<GradeCount> response = seatOccupiedController.getAvailableSeats(performanceId);

		assertEquals(response.getBody(), gradeCountMock);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
}
