package com.ticketease.te.Purchase;

import org.springframework.stereotype.Service;

import com.ticketease.te.account.Account;
import com.ticketease.te.member.Member;
import com.ticketease.te.ticket.Seat;
import com.ticketease.te.ticket.Ticket;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class Purchase {
	MemberSer

	@Transactional
	public void purchaseTicket(final String nickName, final Long ticketId, final Integer requestSeatCount) {

		Member member = memberRepository.findByNickName(nickName)
			.orElseThrow(() -> new RuntimeException("존재하지 않는 이름입니다 닉네임 : " + nickName));
		Ticket ticket = ticketRepository.findById(ticketId)
			.orElseThrow(() -> new RuntimeException("존재하지 않는 상품입니다 상품Id : " + ticketId));
		Account account = accountRepository.findById(member.getAccountId())
			.orElseThrow(() -> new RuntimeException("존재하지 않는 계좌입니다"));
		Seat seat = ticket.getSeat();

		accountService.deductAmount(account, ticket, requestSeatCount);

		seat.reserveSeat(requestSeatCount);
		ticketRepository.save(ticket);

		memberTicketService.registerTicketForMember(member, ticket, requestSeatCount);
	}
}
