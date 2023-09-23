package com.ticketease.te.Purchase;

import org.springframework.stereotype.Service;

import com.ticketease.te.account.Account;
import com.ticketease.te.account.AccountService;
import com.ticketease.te.member.Member;
import com.ticketease.te.member.MemberService;
import com.ticketease.te.ticket.Seat;
import com.ticketease.te.ticket.Ticket;
import com.ticketease.te.ticket.TicketService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class Purchase {

	private final MemberService memberService;
	private final TicketService ticketService;
	private final AccountService accountService;

	@Transactional
	public void purchaseTicket(final String nickName, final Long ticketId, final Integer requestSeatCount) {

		Member member = memberService.findMemberByNickName(nickName);
		Ticket ticket = ticketService.findTicketById(ticketId);
		Account account = accountService.findAccountById(member.getAccountId());
		Seat seat = ticket.getSeat();

		accountService.deductAmount(account, ticket, requestSeatCount);

		seat.reserveSeat(requestSeatCount);
		ticketService.saveTicket(ticket);

		memberTicketService.registerTicketForMember(member, ticket, requestSeatCount);
	}
}
