package com.ticketease.te.memberTicketService;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ticketease.te.member.Member;
import com.ticketease.te.memberticket.MemberTicket;
import com.ticketease.te.memberticket.MemberTicketRepository;
import com.ticketease.te.memberticket.MemberTicketService;
import com.ticketease.te.ticket.Grade;
import com.ticketease.te.ticket.Seat;
import com.ticketease.te.ticket.Ticket;

public class MemberTicketServiceTest {

	@InjectMocks
	private MemberTicketService memberTicketService;

	@Mock
	private MemberTicketRepository memberTicketRepository;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	@DisplayName("멤버에게 티켓 등록 성공")
	void registerTicketForMember_success() {
		Member mockMember = Member.of("ko", "1234");
		mockMember.addAccount(1L);
		Ticket mockTicket = Ticket.of(Seat.of(100, Grade.S), 50_000);
		Integer requestSeatCount = 1;

		memberTicketService.registerTicketForMember(mockMember, mockTicket, requestSeatCount);

		verify(memberTicketRepository, times(1)).save(any(MemberTicket.class));
	}
}

