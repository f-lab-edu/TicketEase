package com.ticketease.te.member;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
	private final MemberRepository memberRepository;

	public Member findMemberByNickName(String nickName) {
		return memberRepository.findByNickName(nickName)
			.orElseThrow(() -> new RuntimeException("존재하지 않는 이름입니다 닉네임 : " + nickName));
	}
}
