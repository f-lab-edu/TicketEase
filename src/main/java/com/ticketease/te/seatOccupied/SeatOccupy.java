package com.ticketease.te.seatOccupied;

import java.util.Map;

public interface SeatOccupy {
	Long incrementSeats(String redisKey, String userKey, Integer requestCnt);

	void setExpire(String key, long timeout);

	Map<Object, Object> getHashEntries(String redisKey);

	String makeRedisKey(String ticketId);
}
