package com.Ticketease.TE.member.signUp;

import com.Ticketease.TE.exception.SignUpExceptionHandler;
import com.Ticketease.TE.member.Member;
import com.Ticketease.TE.member.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
class SignUpServiceTest {

    @Autowired
    private SignUpService signUpService;
    @MockBean
    private MemberRepository memberRepository;

    @Test
    @DisplayName("회원 가입 - 아이디 길이부족 예외발생")
    void givenSighUpDto_WhenHasIdLengthError_thenValidationFail() {
        //given
        SignUpDto signUpDto = SignUpDto.of("hel", "1234qwer", "1234qwer");
        BindingResult bindingResult = new BeanPropertyBindingResult(signUpDto, "signUpDto");

        //when
        bindingResult.addError(new ObjectError("signUpDto", "Passwords length"));

        //then
        assertThrows(SignUpExceptionHandler.class,() -> signUpService.signUpUserValidation(signUpDto,bindingResult));
    }

    @Test
    @DisplayName("회원 가입 - 패스워드 불일치 예외발생")
    void givenSighUpDto_WhenPasswordInValid_thenValidationFail() {
        //given
        SignUpDto signUpDto = SignUpDto.of("hello", "1234qr", "1234qwer");
        BindingResult bindingResult = new BeanPropertyBindingResult(signUpDto, "signUpDto");

        //when

        //then
        assertThrows(SignUpExceptionHandler.class,() -> signUpService.signUpUserValidation(signUpDto,bindingResult));
    }

    @Test
    @DisplayName("회원 가입 - 정상적인 요청 가입성공")
    void givenSighUpDto_WhenVaild_thenValidationSuccess() {
        //given
        SignUpDto signUpDto = SignUpDto.of("hello", "1234qwer", "1234qwer");
        BindingResult bindingResult = new BeanPropertyBindingResult(signUpDto, "signUpDto");

        //when

        //then
        assertDoesNotThrow(() -> signUpService.signUpUserValidation(signUpDto,bindingResult));
    }

    @Test
    @DisplayName("회원 가입 - 중복 닉네임 없을때 가입성공")
    void givenSighUpDto_WhenNoDuplicateName_thenSignUpSuccess() {
        //given
        SignUpDto signUpDto = SignUpDto.of("hellWorld", "1234qwer", "1234qwer");

        //when
        when(memberRepository.findByNickName("existingNickname")).thenReturn(Optional.empty());

        //then
        assertDoesNotThrow(() -> signUpService.signUpUser(signUpDto));
    }

    @Test
    @DisplayName("회원 가입 - 중복 닉네임 존재 예외발생")
    void givenSighUpDto_WhenDuplicateNameExist_thenSignUpFail() {
        //given
        SignUpDto signUpDto = SignUpDto.of("hellWorld", "1234qwer", "1234qwer");

        //when
        when(memberRepository.findByNickName(signUpDto.nickName())).thenReturn(Optional.of(Member.of(signUpDto.nickName(), signUpDto.password())));

        //then
        assertThrows(SignUpExceptionHandler.class, () -> signUpService.signUpUser(signUpDto));
    }

}