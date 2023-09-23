package com.ticketease.te.memberticket;

import org.springframework.stereotype.Service;

import com.ticketease.te.member.Member;
import com.ticketease.te.member.MemberService;
import com.ticketease.te.ticket.Ticket;
import com.ticketease.te.ticket.TicketService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberTicketService {
	private final MemberService memberService;
	private final TicketService ticketService;
	private final MemberTicketRepository memberTicketRepository;

	public void registerTicketForMember(String nickName, Long ticketId, Integer requestSeatCount) {
		Member member = memberService.findMemberByNickName(nickName);
		Ticket ticket = ticketService.findTicketById(ticketId);
		memberTicketSave(member.getId(), ticket.getId(),
			ticket.getFixedPrice(), requestSeatCount);
	}

	public void memberTicketSave(Long memberId, Long ticketId, Integer ticketPrice, Integer requestSeatCount) {
		MemberTicket memberTicket = MemberTicket.of(memberId, ticketId,
			ticketPrice, requestSeatCount);
		memberTicketRepository.save(memberTicket);
	}
}
