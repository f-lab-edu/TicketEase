package com.Ticketease.TE.member.signUp;

import com.Ticketease.TE.exception.SignUpExceptionHandler;
import com.Ticketease.TE.exception.ExceptionCode;
import com.Ticketease.TE.member.Member;
import com.Ticketease.TE.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SignUpService {

    private final MemberRepository memberRepository;

    public Long signUpUserValidation(SignUpDto dto, BindingResult result){

        if(result.hasErrors() || !dto.password().equals(dto.confirmPassword())){
            throw new SignUpExceptionHandler(ExceptionCode.INVALID_SIGNUP_REQUEST,ExceptionCode.INVALID_SIGNUP_REQUEST.getDescription());
        }
        return 0L;
    }

    public Long signUpUser(SignUpDto signUpDto){
        Optional<Member> member = memberRepository.findByNickName(signUpDto.nickName());
        if (member.isEmpty()) {
            memberRepository.save(SignUpDto.toEntity(signUpDto));
            return 0L;
        }else{
            throw new SignUpExceptionHandler(ExceptionCode.USER_ALREADY_EXIST, ExceptionCode.USER_ALREADY_EXIST.getDescription());
        }
    }
}
