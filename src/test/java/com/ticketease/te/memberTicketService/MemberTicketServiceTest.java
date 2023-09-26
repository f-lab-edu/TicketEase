package com.ticketease.te.memberTicketService;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ticketease.te.member.Member;
import com.ticketease.te.member.MemberDataAccessService;
import com.ticketease.te.memberticket.MemberTicketDataAccessService;
import com.ticketease.te.memberticket.MemberTicketService;
import com.ticketease.te.ticket.Grade;
import com.ticketease.te.ticket.Seat;
import com.ticketease.te.ticket.Ticket;
import com.ticketease.te.ticket.TicketDataAccessService;

public class MemberTicketServiceTest {

	@InjectMocks
	private MemberTicketService memberTicketService;

	@Mock
	private MemberTicketDataAccessService memberTicketDataAccessService;

	@Mock
	private MemberDataAccessService memberDataAccessService;

	@Mock
	private TicketDataAccessService ticketDataAccessService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	@DisplayName("멤버에게 티켓 등록 성공")
	void registerTicketForMember_success() {
		String nickName = "testUser";
		Long ticketId = 1L;
		Integer requestSeatCount = 2;
		Member mockMember = Member.of(nickName, "password");
		Ticket mockTicket = Ticket.of(Seat.of(100, Grade.S), 5000);

		when(memberDataAccessService.findMemberByNickName(nickName)).thenReturn(mockMember);

		when(ticketDataAccessService.findTicketById(ticketId)).thenReturn(mockTicket);

		memberTicketService.registerTicketForMember(nickName, ticketId, requestSeatCount);

		verify(memberTicketDataAccessService, times(1)).assignSeatsToMember(
			mockMember.getId(), mockTicket.getId(), mockTicket.getFixedPrice(), requestSeatCount);
	}
}

