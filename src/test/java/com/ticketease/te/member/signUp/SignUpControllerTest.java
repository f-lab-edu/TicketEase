package com.ticketease.te.member.signUp;

import com.ticketease.te.exception.ExceptionCode;
import com.ticketease.te.exception.SignUpExceptionHandler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("회원가입 컨트롤러")
@WebMvcTest(SignUpController.class)
class SignUpControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private SignUpService signUpService;

    @Test
    @DisplayName("회원가입페이지 접근")
    void givenNothing_whenRequestSignUpPage_thenReturnSignUpPage() throws Exception {
        //given

        //when then
        mvc.perform(get("/signup"))
                .andExpect(status().isOk())
                .andExpect(view().name("signup"));
    }


    @Test
    @DisplayName("유효하지 않은 요청(검증실패)")
    void givenSignUpRequest_whenInvalidRequest_thenSignUpFail() throws Exception {
        //given
        SignUpRequest request = SignUpRequest.of("admin", "1234qwe", "1234qwer");

        //when then
        mvc.perform(post("/signup")
                        .param("nickname", request.nickname())
                        .param("password", request.password())
                        .param("confirmPassword", request.confirmPassword()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:signup"))
                .andExpect(flash().attribute(ExceptionCode.INVALID_SIGNUP_REQUEST.toString(), ExceptionCode.INVALID_SIGNUP_REQUEST.getDescription()));
    }


    @Test
    @DisplayName("유효하지 않은 요청(이름중복)")
    void givenSignUpRequest_whenDuplicateName_thenSignUpFail() throws Exception {
        //given
        SignUpRequest request = SignUpRequest.of("admin", "1234qwer", "1234qwer");

        //when
        when(signUpService.signUpUser(any(SignUpDto.class)))
                .thenThrow(new SignUpExceptionHandler(ExceptionCode.USER_ALREADY_EXIST, ExceptionCode.USER_ALREADY_EXIST.getDescription()));

        //then
        mvc.perform(post("/signup")
                        .param("nickname", request.nickname())
                        .param("password", request.password())
                        .param("confirmPassword", request.confirmPassword()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:signup"))
                .andExpect(flash().attribute(ExceptionCode.USER_ALREADY_EXIST.toString(), ExceptionCode.USER_ALREADY_EXIST.getDescription()));
    }

    @Test
    @DisplayName("유효한 요청(가입 성공)")
    void givenSignUpRequest_whenValidRequest_thenSignUpSuccess() throws Exception {
        //given
        SignUpRequest request = SignUpRequest.of("helloWorld", "1234qwer", "1234qwer");

        //when
        when(signUpService.signUpUser(any(SignUpDto.class))).thenReturn(0L);

        //then
        mvc.perform(post("/signup")
                        .param("nickname", request.nickname())
                        .param("password", request.password())
                        .param("confirmPassword", request.confirmPassword()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:login"))
                .andExpect(flash().attribute("success","회원가입이 완료됐습니다."));

    }
}