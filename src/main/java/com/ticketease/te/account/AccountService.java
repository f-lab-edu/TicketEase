package com.ticketease.te.account;

import org.springframework.stereotype.Service;

import com.ticketease.te.exception.ExceptionCode;
import com.ticketease.te.exception.ExceptionHandler;
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

	public Long deductAmount(String nickname, int totalPrice) {
		Account account = accountRepository.findAccountByMemberNickName(nickname)
			.orElseThrow(() -> new ExceptionHandler(
				ExceptionCode.ACCOUNT_NOT_FOUND, ExceptionCode.ACCOUNT_NOT_FOUND.getDescription()));
		account.deductAmounts(totalPrice);
		return accountRepository.save(account).getMemberId();
	}
}
