package com.ticketease.te.ticket;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ticketease.te.exception.ExceptionCode;
import com.ticketease.te.exception.ExceptionHandler;
import com.ticketease.te.performance.Performance;
import com.ticketease.te.performance.PerformanceDateTime;

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
		final String performanceStartDateTime = "2023-01-01T00:00:00";
		final String performanceEndDateTime = "2023-01-31T01:00:00";
		PerformanceDateTime performanceDateTime = PerformanceDateTime.of(
			LocalDateTime.parse(performanceStartDateTime),
			LocalDateTime.parse(performanceEndDateTime));
		Performance performance = Performance.of(name, description, performanceDateTime);

		List<Ticket> tickets = Arrays.asList(
			Ticket.of(Seat.of(10, Grade.VIP), 100000),
			Ticket.of(Seat.of(100, Grade.R), 80000),
			Ticket.of(Seat.of(1000, Grade.S), 50000)
		);

		tickets.forEach(ticket -> performance.addTicketId(ticket.getId()));

		when(ticketRepository.findAllById(performance.getTicketIds())).thenReturn(tickets);

		Map<Grade, Integer> map = new HashMap<>();
		map.put(Grade.VIP, 10);
		map.put(Grade.R, 100);
		map.put(Grade.S, 1000);

		GradeCount expected = GradeCount.of(map);
		GradeCount actual = ticketService.countTicketByGradeForPerformance(performance);

		assertEquals(expected, actual);
	}

	@Test
	@DisplayName("티켓을 찾을 수 없어서 좌석 예약에 실패하다")
	void givenReserveSeatRequest_whenTicketNotFound_thenFail() throws Exception {
		//given

		//when
		when(ticketRepository.findById(anyLong())).thenThrow(
			new ExceptionHandler(ExceptionCode.TICKET_NOT_FOUND, ExceptionCode.TICKET_NOT_FOUND.getDescription()));

		//then
		assertThrows(ExceptionHandler.class, () -> ticketService.ReserveSeat(1L, 100));

	}

	@Test
	@DisplayName("잔여 좌석이 부족해서 좌석 예약에 실패하다")
	void givenReserveSeatRequest_whenLackOfSeat_thenFail() throws Exception {
		//given
		Ticket mockTicket = mock(Ticket.class);
		Seat mockSeat = Seat.of(1, mock(Grade.class));
		//when
		when(ticketRepository.findById(anyLong())).thenReturn(Optional.of(mockTicket));
		when(mockTicket.getSeat()).thenReturn(mockSeat);
		//then
		ExceptionHandler handler = assertThrows(ExceptionHandler.class, () -> ticketService.ReserveSeat(1L, 100));
		assertEquals(handler.getCode(), ExceptionCode.LACK_OF_TICKET_SEAT);

	}

	@Test
	@DisplayName("좌석 예약에 성공하다")
	void givenReserveSeatRequest_whenValid_thenSuccess() throws Exception {
		//given
		Ticket mockTicket = mock(Ticket.class);
		Seat mockSeat = Seat.of(100, mock(Grade.class));
		//when
		when(ticketRepository.findById(anyLong())).thenReturn(Optional.of(mockTicket));
		when(mockTicket.getSeat()).thenReturn(mockSeat);
		//then
		assertDoesNotThrow(() -> ticketService.ReserveSeat(1L, 100));

	}

}
