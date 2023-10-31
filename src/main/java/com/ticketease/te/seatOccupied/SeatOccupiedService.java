package com.ticketease.te.seatOccupied;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ticketease.te.performance.PerformanceDataAccessService;
import com.ticketease.te.performance.TicketCountFacadeService;
import com.ticketease.te.ticket.Grade;
import com.ticketease.te.ticket.GradeCount;
import com.ticketease.te.ticket.Ticket;
import com.ticketease.te.ticket.TicketDataAccessService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SeatOccupiedService {
	private final SeatOccupy seatOccupy;
	private final TicketCountFacadeService ticketCountFacadeService;
	private final PerformanceDataAccessService performanceDataAccessService;
	private final TicketDataAccessService ticketDataAccessService;

	public void occupiedSeat(String ticketId, Long userId, int requestCount) {

		String userKey = String.valueOf(userId);
		String redisKey = seatOccupy.makeRedisKey(ticketId);

		Long remainingSeats = seatOccupy.incrementSeats(redisKey, userKey, requestCount);

		if (remainingSeats < 0) {
			seatOccupy.incrementSeats(redisKey, userKey, -requestCount);
		}

		seatOccupy.setExpire(redisKey, 5);
	}

	public GradeCount getAvailableSeats(Long performanceId) {
		List<Long> ticketIds = performanceDataAccessService.findBy(performanceId).getTicketIds();

		GradeCount totalSeats = ticketCountFacadeService.countTicketByGradeForPerformance(performanceId);

		List<Ticket> tickets = ticketDataAccessService.findTicketsBy(ticketIds);
		// 각 seatId에 대한 선점 좌석 수 계산
		Map<Grade, Integer> reservedSeats = new EnumMap<>(Grade.class);
		for (Ticket ticket : tickets) {
			int reservedCount = getReservedCountBySeatId(String.valueOf(ticket.getId()));
			Grade grade = ticket.getGrade();
			reservedSeats.put(grade, reservedSeats.getOrDefault(grade, 0) + reservedCount);
		}

		// 등급별 좌석 개수 - 선점된 좌석의 수
		Map<Grade, Integer> availableSeats = new EnumMap<>(Grade.class);
		for (Grade grade : Grade.values()) {
			int total = totalSeats.gradeCountMap().getOrDefault(grade, 0);
			int reserved = reservedSeats.getOrDefault(grade, 0);
			availableSeats.put(grade, total - reserved);
		}

		return GradeCount.of(availableSeats);
	}

	public int getReservedCountBySeatId(String ticketId) {
		String redisKey = seatOccupy.makeRedisKey(ticketId);
		Map<Object, Object> userReservations = seatOccupy.getHashEntries(redisKey);

		int totalReserved = 0;
		for (Object count : userReservations.values()) {
			totalReserved += (Integer)count;
		}

		return totalReserved;
	}
}
