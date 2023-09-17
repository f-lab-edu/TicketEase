package com.ticketease.te.performance.board;

import com.ticketease.te.performance.PerformanceDateTime;

public record BoardResponse(
	Long id,
	String name,
	PerformanceDateTime time,
	String description
) {
	public static BoardResponse from(BoardDto dto) {
		return new BoardResponse(dto.id(), dto.name(), dto.time(), dto.description());
	}

	public static BoardResponse of(BoardDto dto) {
		return new BoardResponse(dto.id(), dto.name(), dto.time(), null);
	}
}
