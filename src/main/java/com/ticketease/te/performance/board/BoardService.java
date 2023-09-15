package com.ticketease.te.performance.board;

import java.util.List;
import java.util.stream.IntStream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ticketease.te.performance.PerformanceRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardService {

	private static final int BAR_LENGTH = 5;
	private final PerformanceRepository performanceRepository;

	public Page<BoardDto> findPerformance(String search, Pageable pageable) {
		if (search == null || search.isEmpty() || search.isBlank()) {
			return performanceRepository.findAll(pageable).map(BoardDto::from);
		}
		return performanceRepository.findByNameContaining(search, pageable).map(BoardDto::from);
	}

	public List<Integer> getPaginationBarNumbers(int currentPageNumber, int totalPages) {
		int startNumber = Math.max(currentPageNumber - (BAR_LENGTH / 2), 0);
		int endNumber = Math.min(startNumber + BAR_LENGTH, totalPages);
		return IntStream.range(startNumber, endNumber).boxed().toList();
	}

	public BoardDto findSinglePerformanceById(Long id) {
		return performanceRepository.findById(id).map(BoardDto::from)
			.orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다."));
	}

}
