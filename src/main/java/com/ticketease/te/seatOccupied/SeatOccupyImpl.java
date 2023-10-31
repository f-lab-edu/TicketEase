package com.ticketease.te.seatOccupied;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SeatOccupyImpl implements SeatOccupy {
	private final StringRedisTemplate redisTemplate;

	public Long incrementSeats(String redisKey, String userKey, Integer requestCnt) {
		Long remainingSeats = redisTemplate.opsForHash().increment(redisKey, userKey, -requestCnt);
		return remainingSeats;
	}

	public void setExpire(String redisKey, long timeout) {
		redisTemplate.expire(redisKey, 5, TimeUnit.MINUTES);
	}

	public Map<Object, Object> getHashEntries(String redisKey) {
		return redisTemplate.opsForHash().entries(redisKey);
	}

	public String makeRedisKey(String ticketId) {
		return "TICKET_ID: " + ticketId;
	}

}
