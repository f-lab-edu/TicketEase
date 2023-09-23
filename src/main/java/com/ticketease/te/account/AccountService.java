package com.ticketease.te.account;

import org.springframework.stereotype.Service;

import com.ticketease.te.ticket.Ticket;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountService {
	private final AccountRepository accountRepository;

	public void deductAmount(Account account, Ticket ticket, Integer requestSeatCount) {
		Integer totalPaymentAmount = ticket.getFixedPrice() * requestSeatCount;
		account.deductAmount(totalPaymentAmount);
		accountRepository.save(account);
	}

	public Account findAccountById(Long accountId) {
		return accountRepository.findById(accountId)
			.orElseThrow(() -> new RuntimeException("존재하지 않는 계좌입니다"));
	}
}
