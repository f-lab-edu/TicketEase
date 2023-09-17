package com.ticketease.te.performance.board;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import com.ticketease.te.config.Security;
import com.ticketease.te.performance.PerformanceDateTime;

import jakarta.persistence.EntityNotFoundException;

@DisplayName("게시판 컨트롤러")
@WebMvcTest(BoardController.class)
@Import(Security.class)
class BoardControllerTest {

	@Autowired
	private MockMvc mvc;
	@MockBean
	private BoardService boardService;

	@Test
	@DisplayName("유효한 요청 - 공연 상세페이지")
	void givenPerformanceId_whenPerformanceExist_thenReturnPerformanceDetail() throws Exception {
		//given
		Long PerformanceId = 1L;
		BoardDto mockBoardDto = BoardDto.of(1L, "싸이", "싸이",
			PerformanceDateTime.of(LocalDateTime.now(), LocalDateTime.now()));

		//when
		given(boardService.findSinglePerformanceById(PerformanceId)).willReturn(mockBoardDto);

		//then
		mvc.perform(get("/detail/" + PerformanceId))
			.andExpect(status().isOk())
			.andExpect(view().name("detail"))
			.andExpect(model().attributeExists("Board"));
	}

	@Test
	@DisplayName("유효하지 않은 요청 - 공연이 없을 때")
	void givenPerformanceId_whenPerformanceNotExist_thenReturnIndex() throws Exception {
		//given
		Long PerformanceId = 1L;

		//when
		given(boardService.findSinglePerformanceById(PerformanceId)).willThrow(EntityNotFoundException.class);

		//then
		mvc.perform(get("/detail/" + PerformanceId))
			.andExpect(status().isOk())
			.andExpect(view().name("error"));
	}

}
