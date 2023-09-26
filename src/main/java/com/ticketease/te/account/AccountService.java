package com.ticketease.te.account;

import org.springframework.stereotype.Service;

import com.ticketease.te.exception.ExceptionCode;
import com.ticketease.te.exception.ExceptionHandler;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountService {
	private final AccountRepository accountRepository;

	public Long deductAmount(String nickname, int totalPrice) {
		Account account = accountRepository.findAccountByMemberNickName(nickname)
			.orElseThrow(() -> new ExceptionHandler(
				ExceptionCode.ACCOUNT_NOT_FOUND, ExceptionCode.ACCOUNT_NOT_FOUND.getDescription()));
		account.deductAmounts(totalPrice);
		return accountRepository.save(account).getMember().getId();
	}
}
