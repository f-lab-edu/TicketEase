package com.ticketease.te.ticket;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.ticketease.te.Purchase.PurchaseController;
import com.ticketease.te.Purchase.PurchaseTicketLock;

public class PurchaseControllerTest {
	private MockMvc mockMvc;

	@InjectMocks
	private PurchaseController purchaseController;

	@Mock
	private PurchaseTicketLock purchaseTicketLock;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(purchaseController).build();
	}

	@Test
	@DisplayName("티켓 예약 성공")
	void reserveTicket_success() throws Exception {
		// Given
		final String nickName = "testUser";
		final Long ticketId = 1L;
		final Integer requestSeatCount = 1;

		doNothing().when(purchaseTicketLock).purchaseInOrder(nickName, ticketId, requestSeatCount);

		// When & Then
		mockMvc.perform(post("/api/tickets/ticketReserve")
				.param("nickName", nickName)
				.param("ticketId", ticketId.toString())
				.param("requestSeatCount", requestSeatCount.toString())
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isNoContent());
	}
}

