package com.ticketease.te.memberticket;

import org.springframework.stereotype.Service;

import com.ticketease.te.member.Member;
import com.ticketease.te.ticket.Ticket;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberTicketService {
	private final MemberTicketRepository memberTicketRepository;

	public void registerTicketForMember(Member member, Ticket ticket, Integer requestSeatCount) {
		MemberTicket memberTicket = MemberTicket.of(member.getId(), ticket.getId(),
			ticket.getFixedPrice(), requestSeatCount);
		memberTicketRepository.save(memberTicket);
	}
}
