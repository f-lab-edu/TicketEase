package com.ticketease.te.memberticket;

import org.springframework.stereotype.Service;

import com.ticketease.te.member.Member;
import com.ticketease.te.member.MemberDataAccessService;
import com.ticketease.te.ticket.Ticket;
import com.ticketease.te.ticket.TicketDataAccessService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberTicketService {
	private final MemberTicketDataAccessService memberTicketDataAccessService;
	private final MemberDataAccessService memberDataAccessService;
	private final TicketDataAccessService ticketDataAccessService;

	public void registerTicketForMember(String nickName, Long ticketId, Integer requestSeatCount) {
		Member member = memberDataAccessService.findMemberByNickName(nickName);
		Ticket ticket = ticketDataAccessService.findTicketById(ticketId);
		memberTicketDataAccessService.assignSeatsToMember(member.getId(), ticket.getId(),
			ticket.getFixedPrice(), requestSeatCount);
	}
}
