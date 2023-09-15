package com.ticketease.te.member.Login;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import com.ticketease.te.config.Security;

@DisplayName("로그인 컨트롤러")
@WebMvcTest(LoginController.class)
@Import(Security.class)
class LoginControllerTest {
	@Autowired
	private MockMvc mvc;

	@Test
	@DisplayName("로그인페이지 접근")
	void givenNothing_whenRequestLoginPage_thenReturnLoginPage() throws Exception {
		mvc.perform(get("/login"))
			.andExpect(status().isOk())
			.andExpect(view().name("login"));
	}
}
