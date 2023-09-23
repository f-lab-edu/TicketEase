package com.ticketease.te.Purchase;

import org.springframework.stereotype.Service;

import com.ticketease.te.account.AccountService;
import com.ticketease.te.member.Member;
import com.ticketease.te.member.MemberService;
import com.ticketease.te.memberticket.MemberTicketService;
import com.ticketease.te.ticket.Seat;
import com.ticketease.te.ticket.TicketService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PurchaseService {

	private final MemberService memberService;
	private final TicketService ticketService;
	private final AccountService accountService;
	private final MemberTicketService memberTicketService;

	@Transactional
	public void purchaseTicket(final String nickName, final Long ticketId, final Integer requestSeatCount) {

		Member member = memberService.findMemberByNickName(nickName);

		accountService.deductAmount(member.getAccountId(), ticketId, requestSeatCount);

		Seat seat = ticketService.getSeat(ticketId);
		seat.reserveSeat(requestSeatCount);

		ticketService.saveTicket(ticketId);

		memberTicketService.registerTicketForMember(nickName, ticketId, requestSeatCount);
	}
}
