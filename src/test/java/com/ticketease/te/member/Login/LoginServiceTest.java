package com.ticketease.te.member.Login;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.ticketease.te.member.Member;
import com.ticketease.te.member.MemberRepository;

@DisplayName("로그인 서비스")
@ExtendWith(MockitoExtension.class)
class LoginServiceTest {

	@InjectMocks
	private LoginService loginService;

	@Mock
	private MemberRepository memberRepository;

	private final String username = "helloWorld";
	private final String password = "1234qwer";

	@DisplayName("유효한 요청")
	@Test
	void givenLoginRequest_whenValid_LoginSuccess() {
		//given
		LoginRequest request = LoginRequest.of(username, password);

		//when
		when(memberRepository.findByNickName(request.nickname())).thenReturn(
			Optional.of(Member.of(request.nickname(), request.password())));

		//then
		assertDoesNotThrow(() -> loginService.loadUserByUsername(request.nickname()));

	}

	@DisplayName("유효하지 않은 요청, 사용자 인증실패")
	@Test
	void givenLoginRequest_whenInValid_LoginFail() {
		//given
		LoginRequest request = LoginRequest.of(username, password);

		//when
		when(memberRepository.findByNickName(request.nickname())).thenReturn(Optional.empty());

		//then
		assertThrows(UsernameNotFoundException.class, () -> loginService.loadUserByUsername(request.nickname()));

	}

}
