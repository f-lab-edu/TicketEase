package com.ticketease.te.purchaseticket;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import com.ticketease.te.config.Security;
import com.ticketease.te.exception.ExceptionCode;
import com.ticketease.te.exception.ExceptionHandler;

@WebMvcTest(PurchaseTicketController.class)
@Import(Security.class)
class PurchaseTicketControllerTest {

	@Autowired
	MockMvc mvc;

	@MockBean
	private PurchaseTicketService orchestrationService;

	private final String nickname = "hello";
	private final Long ticketId = 1L;
	private final Integer requestSeatCount = 10;

	@Test
	@DisplayName("티켓구매에 성공하다")
	void givenPurchaseTicketRequest_whenValid_thenSuccess() throws Exception {
		//given

		//when
		when(orchestrationService.PurchaseTicket(nickname, ticketId, requestSeatCount)).thenReturn(1L);

		//then
		mvc.perform(post("/api/purchase-ticket")
				.param("nickname", nickname)
				.param("ticketId", String.valueOf(ticketId))
				.param("requestSeatCount", String.valueOf(requestSeatCount))
				.with(csrf()))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.message", is("구매에 성공했습니다.")));
	}

	@Test
	@DisplayName("돈이 부족해서 티켓 구매에 실패하다")
	void givenPurchaseTicketRequest_whenLackOfMoney_thenFail() throws Exception {
		//given

		//when
		when(orchestrationService.PurchaseTicket(nickname, ticketId, requestSeatCount)).thenThrow(new ExceptionHandler(
			ExceptionCode.LACK_OF_MONEY, ExceptionCode.LACK_OF_MONEY.getDescription()));

		//then
		mvc.perform(post("/api/purchase-ticket")
				.param("nickname", nickname)
				.param("ticketId", String.valueOf(ticketId))
				.param("requestSeatCount", String.valueOf(requestSeatCount))
				.with(csrf()))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.name", is(ExceptionCode.LACK_OF_MONEY.name())));
	}

	@Test
	@DisplayName("계정을 찾을 수 없어서 티켓 구매에 실패하다")
	void givenPurchaseTicketRequest_whenCannotFoundMember_thenFail() throws Exception {
		//given

		//when
		when(orchestrationService.PurchaseTicket(nickname, ticketId, requestSeatCount)).thenThrow(new ExceptionHandler(
			ExceptionCode.MEMBER_NOT_FOUND, ExceptionCode.MEMBER_NOT_FOUND.getDescription()));

		//then
		mvc.perform(post("/api/purchase-ticket")
				.param("nickname", nickname)
				.param("ticketId", String.valueOf(ticketId))
				.param("requestSeatCount", String.valueOf(requestSeatCount))
				.with(csrf()))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.name", is(ExceptionCode.MEMBER_NOT_FOUND.name())));
	}
}
