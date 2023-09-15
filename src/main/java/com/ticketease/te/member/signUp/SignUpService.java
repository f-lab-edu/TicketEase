package com.ticketease.te.member.signUp;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ticketease.te.exception.ExceptionCode;
import com.ticketease.te.exception.ExceptionHandler;
import com.ticketease.te.member.Member;
import com.ticketease.te.member.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SignUpService {

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;

	@Transactional
	public Long signUpUser(SignUpDto signUpDto) {
		Optional<Member> member = memberRepository.findByNickName(signUpDto.nickName());
		if (member.isEmpty()) {
			memberRepository.save(
				SignUpDto.toEntity(signUpDto.nickName(), passwordEncoder.encode(signUpDto.password())));
			return 0L;
		} else {
			throw new ExceptionHandler(ExceptionCode.USER_ALREADY_EXIST,
				ExceptionCode.USER_ALREADY_EXIST.getDescription());
		}
	}
}
