package com.ticketease.te.performance.board;

import com.ticketease.te.performance.Performance;
import com.ticketease.te.performance.PerformanceDateTime;

public record BoardDto(
	Long id,
	String name,
	String description,
	PerformanceDateTime time
) {
	public static BoardDto from(Performance performance) {
		return new BoardDto(performance.getId(), performance.getName(), performance.getDescription(),
			performance.getPerformanceDateTime());
	}

	public static BoardDto of(Long id, String name, String description, PerformanceDateTime time) {
		return new BoardDto(id, name, description, time);
	}

}
