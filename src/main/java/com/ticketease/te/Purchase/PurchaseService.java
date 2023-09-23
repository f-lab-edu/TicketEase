package com.ticketease.te.Purchase;

import org.springframework.stereotype.Service;

import com.ticketease.te.account.AccountService;
import com.ticketease.te.member.MemberService;
import com.ticketease.te.memberticket.MemberTicketService;
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
		Long accountId = accountService.findAccountIdByNickName(nickName);
		accountService.deductAmount(accountId, ticketId, requestSeatCount);
		ticketService.reserveSeat(ticketId, requestSeatCount);
		memberTicketService.registerTicketForMember(nickName, ticketId, requestSeatCount);
	}
}
