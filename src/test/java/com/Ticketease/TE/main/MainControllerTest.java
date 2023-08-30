package com.Ticketease.TE.main;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(MainControllerTest.class)
class MainControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("메인페이지 확인")
    void giveNothing_whenRequestMainPage_thenReturnMainPage() throws Exception{
        //given

        //when
        mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
        //then

    }
}