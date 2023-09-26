package com.ticketease.te.account;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountService {
	private final AccountDataAccessService accountDataAccessService;

	public void deductAmountOnPayment(String nickName, Integer totalPaymentAmount) {
		Long accountId = accountDataAccessService.findAccountIdByNickName(nickName);
		Account account = accountDataAccessService.findAccountByAccountId(accountId);
		account.deductAmount(totalPaymentAmount);
		accountDataAccessService.renewAccount(account);
	}
}
