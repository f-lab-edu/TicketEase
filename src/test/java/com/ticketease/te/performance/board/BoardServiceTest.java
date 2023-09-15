package com.ticketease.te.performance.board;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.ticketease.te.performance.PerformanceRepository;

@DisplayName("게시판 서비스")
@ExtendWith(MockitoExtension.class)
class BoardServiceTest {

	@InjectMocks
	BoardService boardService;

	@Mock
	PerformanceRepository performanceRepository;

	@Test
	@DisplayName("검색어가 없을때 findAll 호출")
	void givenNothing_whenFindPerformance_thenReturnAll() {
		// Given
		Pageable pageable = PageRequest.of(0, 10);

		// When
		when(performanceRepository.findAll(any(Pageable.class))).thenReturn(Page.empty());
		Page<BoardDto> result = boardService.findPerformance(" ", pageable);

		// Then
		verify(performanceRepository).findAll(any(Pageable.class));
		verify(performanceRepository, never()).findByNameContaining(anyString(), any(Pageable.class));
	}

	@Test
	@DisplayName("검색어가 있을때 findByContaining 호출")
	void givenSearch_whenFindPerformance_thenReturnSearchContaining() {
		//given
		Pageable pageable = PageRequest.of(0, 10);

		// When
		when(performanceRepository.findByNameContaining(anyString(), any(Pageable.class))).thenReturn(Page.empty());
		Page<BoardDto> result = boardService.findPerformance("123123", pageable);

		// Then
		verify(performanceRepository, never()).findAll(any(Pageable.class));
		verify(performanceRepository).findByNameContaining(anyString(), any(Pageable.class));
	}

}
