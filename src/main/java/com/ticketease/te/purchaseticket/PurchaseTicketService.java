package com.ticketease.te.purchaseticket;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ticketease.te.account.AccountService;
import com.ticketease.te.memberticket.MemberTicketService;
import com.ticketease.te.ticket.Ticket;
import com.ticketease.te.ticket.TicketService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PurchaseTicketService {

	private final TicketService ticketService;
	private final AccountService accountService;
	private final MemberTicketService memberTicketService;

	@Transactional
	public Long PurchaseTicket(final String nickName, final Long ticketId, final Integer requestSeatCount) {
		Ticket ticket = ticketService.ReserveSeat(ticketId, requestSeatCount);
		Long memberId = accountService.deductAmount(nickName, ticket.getFixedPrice() * requestSeatCount);
		memberTicketService.registerTicketForMember(memberId, ticket.getId(), ticket.getFixedPrice(), requestSeatCount);
		return 1L;
	}

}
