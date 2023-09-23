package com.ticketease.te.account;

import org.springframework.stereotype.Service;

import com.ticketease.te.member.Member;
import com.ticketease.te.member.MemberService;
import com.ticketease.te.ticket.TicketService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountService {
	private final TicketService ticketService;
	private final MemberService memberService;
	private final AccountRepository accountRepository;

	public void deductAmount(String nickName, Long ticketId, Integer requestSeatCount) {
		Long accountId = findAccountIdByNickName(nickName);
		Account account = findAccountById(accountId);

		Integer totalPaymentAmount = ticketService.calculateTicketPrice(ticketId, requestSeatCount);

		account.deductAmount(totalPaymentAmount);
		saveAccount(account);
	}

	public Long findAccountIdByNickName(String nickName) {
		Member member = memberService.findMemberByNickName(nickName);
		return member.getAccountId();
	}

	public Account findAccountById(Long accountId) {
		return accountRepository.findById(accountId)
			.orElseThrow(() -> new RuntimeException("존재하지 않는 계좌입니다"));
	}

	public void saveAccount(Account account) {
		accountRepository.save(account);
	}
}
