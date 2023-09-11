package com.ticketease.te.main;

import com.ticketease.te.config.Security;
import com.ticketease.te.performance.board.BoardService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("메인페이지 컨트롤러")
@WebMvcTest(MainController.class)
@Import(Security.class)
class MainControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BoardService boardService;

    @Test
    @DisplayName("메인페이지 접근")
    void givenNothing_whenRequestMainPage_thenReturnMainPage() throws Exception{
        //given
        given(boardService.findPerformance(anyString(), any(Pageable.class))).willReturn(Page.empty());

        //when then
        mvc.perform(get("/")
                    .queryParam("search", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    @DisplayName("검색어 입력")
    void givenSearch_whenRequestMainPage_thenReturnPagedResult() throws Exception{
        //given
        String search = "test";
        given(boardService.findPerformance(eq(search), any(Pageable.class))).willReturn(Page.empty());

        // When Then
        mvc.perform(get("/")
                        .queryParam("search", search))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("board"))
                .andExpect(model().attributeExists("paginationBarNumbers"));
        verify(boardService, times(1)).findPerformance(eq(search),any(Pageable.class));

    }
}