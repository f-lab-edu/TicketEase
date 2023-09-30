package com.ticketease.te.account;

public interface AccountReader {
	Long findAccountIdBy(String nickName);

	Account findAccountBy(Long accountId);
}
