package com.ticketease.te.account;

import org.springframework.stereotype.Service;

import com.ticketease.te.member.Member;
import com.ticketease.te.member.MemberDataAccessService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountDataAccessService {
	private final MemberDataAccessService memberDataAccessService;
	private final AccountRepository accountRepository;

	public Long findAccountIdByNickName(String nickName) {
		Member member = memberDataAccessService.findMemberByNickName(nickName);
		return member.getAccountId();
	}

	public Account findAccountByAccountId(Long accountId) {
		return accountRepository.findById(accountId)
			.orElseThrow(() -> new RuntimeException("존재하지 않는 계좌입니다"));
	}

	public void renewAccount(Account account) {
		accountRepository.save(account);
	}
}
