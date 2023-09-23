package com.ticketease.te.account;

import org.springframework.stereotype.Service;

import com.ticketease.te.member.Member;
import com.ticketease.te.member.MemberService;
import com.ticketease.te.ticket.Ticket;
import com.ticketease.te.ticket.TicketService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountService {
	private final TicketService ticketService;
	private final AccountRepository accountRepository;
	private final MemberService memberService;

	public void deductAmount(Long accountId, Long ticketId, Integer requestSeatCount) {
		Account account = findAccountById(accountId);
		Ticket ticket = ticketService.findTicketById(ticketId);
		Integer totalPaymentAmount = ticket.getFixedPrice() * requestSeatCount;
		account.deductAmount(totalPaymentAmount);
		accountRepository.save(account);
	}

	public Account findAccountById(Long accountId) {
		return accountRepository.findById(accountId)
			.orElseThrow(() -> new RuntimeException("존재하지 않는 계좌입니다"));
	}

	public Long findAccountIdByNickName(String nickName) {
		Member member = memberService.findMemberByNickName(nickName);
		return member.getAccountId();
	}
}
