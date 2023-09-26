package com.ticketease.te.member;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class MemberDataAccessServiceTest {

	@InjectMocks
	private MemberDataAccessService memberDataAccessService;

	@Mock
	private MemberRepository memberRepository;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	@DisplayName("존재하는 닉네임으로 멤버 찾기")
	void findMemberByNickName_exist() {
		String testNickName = "testNick";
		Member mockMember = Member.of(testNickName, "testPassword");

		when(memberRepository.findByNickName(testNickName)).thenReturn(Optional.of(mockMember));

		Member foundMember = memberDataAccessService.findMemberByNickName(testNickName);

		assertEquals(mockMember, foundMember);
	}

	@Test
	@DisplayName("존재하지 않는 닉네임으로 멤버 찾을 때 예외 발생")
	void findMemberByNickName_notExist_shouldThrowException() {
		String testNickName = "notExistNick";

		when(memberRepository.findByNickName(testNickName)).thenReturn(Optional.empty());

		assertThrows(RuntimeException.class,
			() -> memberDataAccessService.findMemberByNickName(testNickName));
	}
}
