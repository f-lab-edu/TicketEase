package com.ticketease.te.memberticket;

import org.springframework.stereotype.Service;

import com.ticketease.te.member.Member;
import com.ticketease.te.member.MemberReader;
import com.ticketease.te.ticket.Ticket;
import com.ticketease.te.ticket.TicketDataAccessService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberTicketService {
	private final MemberTicketWriter memberTicketWriter;
	private final MemberReader memberReader;
	private final TicketDataAccessService ticketDataAccessService;

	public void registerTicketForMember(String nickName, Long ticketId, Integer requestSeatCount) {
		Member member = memberReader.findMemberBy(nickName);
		Ticket ticket = ticketDataAccessService.findTicketBy(ticketId);
		memberTicketWriter.assignSeatsToMember(member.getId(), ticket.getId(),
			ticket.getFixedPrice(), requestSeatCount);
	}
}
