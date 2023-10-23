package com.ticketease.te.seatOccupied;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.ticketease.te.performance.PerformanceDataAccessService;
import com.ticketease.te.performance.TicketCountFacadeService;
import com.ticketease.te.ticket.Grade;
import com.ticketease.te.ticket.GradeCount;
import com.ticketease.te.ticket.Ticket;
import com.ticketease.te.ticket.TicketDataAccessService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SeatOccupiedService {
	private StringRedisTemplate redisTemplate;
	private final TicketCountFacadeService ticketCountFacadeService;
	private final PerformanceDataAccessService performanceDataAccessService;
	private final TicketDataAccessService ticketDataAccessService;

	public void occupiedSeat(String ticketId, Long userId, int requestCount) {

		String userKey = String.valueOf(userId);
		Long remainingSeats = redisTemplate.opsForHash().increment(ticketId, userKey, -requestCount);

		if (remainingSeats < 0) {
			redisTemplate.opsForHash().increment(ticketId, userKey, requestCount);
			throw new RuntimeException("좌석이 부족합니다.");
		}

		redisTemplate.expire(ticketId, 5, TimeUnit.MINUTES);
	}

	public int getReservedCountBySeatId(String ticketId) {
		Map<Object, Object> userReservations = redisTemplate.opsForHash().entries(ticketId);

		int totalReserved = 0;
		for (Object count : userReservations.values()) {
			totalReserved += (Integer)count;
		}

		return totalReserved;
	}

	public GradeCount getAvailableSeats(Long performanceId) {
		List<Long> ticketList = performanceDataAccessService.findBy(performanceId).getTicketIds();

		GradeCount totalSeats = ticketCountFacadeService.countTicketByGradeForPerformance(performanceId);

		// 각 seatId에 대한 선점 좌석 수 계산
		Map<Grade, Integer> reservedSeats = new EnumMap<>(Grade.class);
		for (Long ticketId : ticketList) {
			Ticket ticket = ticketDataAccessService.findTicketBy(ticketId);
			int reservedCount = getReservedCountBySeatId(String.valueOf(ticketId));
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

}
