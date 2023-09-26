package com.ticketease.te.memberticket;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberTicketService {
	private final MemberTicketRepository memberTicketRepository;

	@Transactional
	public MemberTicket registerTicketForMember(Long memberId, Long ticketId, Integer fixedPrice,
		Integer requestSeatCount) {
		MemberTicket memberTicket = MemberTicket.of(memberId, ticketId, fixedPrice, requestSeatCount);
		return memberTicketRepository.save(memberTicket);
	}
}
