package com.ticketease.te.account;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ticketease.te.member.Member;
import com.ticketease.te.member.MemberRepository;

@SpringBootTest
class AccountRepositoryTest {

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private AccountRepository accountRepository;

	@Test
	@DisplayName("")
	void given_when_then() throws Exception {
		//given
		Member member = Member.of("hello", "1234qwer");
		memberRepository.save(member);

		Account account = Account.of(1L, member);
		accountRepository.save(account);

		//when
		Account account1 = accountRepository.findAccountByMemberNickName("hello").orElseThrow();

		//then
		assertEquals(account1.getMember().getPassword(), "1234qwer");

	}

}
