package com.Ticketease.TE.member.signUp;

import com.Ticketease.TE.member.signUp.SignUpController;
import com.Ticketease.TE.member.signUp.SignUpRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(SignUpController.class)
class SignUpControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("회원가입페이지 접근")
    void givenNothing_whenRequestSignUpPage_thenReturnSignUpPage() throws Exception {
        //given

        //when
        mvc.perform(get("/signup"))
                .andExpect(status().isOk())
                .andExpect(view().name("signup"));

        //then
    }

    @Test
    @DisplayName("정상적인 회원가입 요청")
    void givenSignUpRequest_whenValid_thenRedirectLogin() throws Exception{
        //given
        SignUpRequest request = SignUpRequest.of("test123", "1234qwer", "1234qwer");

        //when then
        mvc.perform(post("/signup")
                        .param("nickname", request.nickname())
                        .param("password", request.password())
                        .param("confirmPassword", request.confirmPassword()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:login"));
    }

    @Test
    @DisplayName("비정상적인 회원가입 요청 -> password 길이부족")
    void givenSignUpRequest_whenInValidLengthPassword_thenRedirectSignUp() throws Exception{
        //given
        SignUpRequest request = SignUpRequest.of("test123", "1234", "1234");

        //when then
        mvc.perform(post("/signup")
                        .param("nickname", request.nickname())
                        .param("password", request.password())
                        .param("confirmPassword", request.confirmPassword()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:signup"));
    }

    @Test
    @DisplayName("비정상적인 회원가입 요청 -> nickname 길이 부족")
    void givenSignUpRequest_whenInValidLengthNickname_thenRedirectSignUp() throws Exception{
        //given
        SignUpRequest request = SignUpRequest.of("test", "1234qwer", "1234qwer");

        //when then
        mvc.perform(post("/signup")
                        .param("nickname", request.nickname())
                        .param("password", request.password())
                        .param("confirmPassword", request.confirmPassword()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:signup"));
    }

    @Test
    @DisplayName("비정상적인 회원가입 요청 -> password 불일치")
    void givenSignUpRequest_whenInValidConfirmPassword_thenRedirectSignUp() throws Exception{
        //given
        SignUpRequest request = SignUpRequest.of("test123", "1234qwer", "qwer1234");

        //when then
        mvc.perform(post("/signup")
                        .param("nickname", request.nickname())
                        .param("password", request.password())
                        .param("confirmPassword", request.confirmPassword()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:signup"));
    }
}