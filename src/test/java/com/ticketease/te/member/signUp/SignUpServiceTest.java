package com.ticketease.te.member.signUp;

import com.ticketease.te.exception.SignUpExceptionHandler;
import com.ticketease.te.member.Member;
import com.ticketease.te.member.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@DisplayName("회원가입 서비스")
@ExtendWith(MockitoExtension.class)
class SignUpServiceTest {

    @InjectMocks
    private SignUpService signUpService;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("유효한 요청")
    void givenSighUpDto_WhenNoDuplicateName_thenSignUpSuccess() {
        //given
        SignUpDto signUpDto = SignUpDto.of("hellWorld", "1234qwer", "1234qwer");

        //when
        when(memberRepository.findByNickName(signUpDto.nickName())).thenReturn(Optional.empty());

        //then
        assertDoesNotThrow(() -> signUpService.signUpUser(signUpDto));
    }

    @Test
    @DisplayName("유효하지 않은 요청, 중복 닉네임 O")
    void givenSighUpDto_WhenDuplicateNameExist_thenSignUpFail() {
        //given
        SignUpDto signUpDto = SignUpDto.of("hellWorld", "1234qwer", "1234qwer");

        //when
        when(memberRepository.findByNickName(signUpDto.nickName())).thenReturn(Optional.of(Member.of(signUpDto.nickName(), signUpDto.password())));

        //then
        assertThrows(SignUpExceptionHandler.class, () -> signUpService.signUpUser(signUpDto));
    }

}