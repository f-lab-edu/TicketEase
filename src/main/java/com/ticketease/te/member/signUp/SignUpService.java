package com.ticketease.te.member.signUp;

import com.ticketease.te.exception.ExceptionCode;
import com.ticketease.te.exception.SignUpExceptionHandler;
import com.ticketease.te.member.Member;
import com.ticketease.te.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SignUpService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long signUpUser(SignUpDto signUpDto){
        Optional<Member> member = memberRepository.findByNickName(signUpDto.nickName());
        if (member.isEmpty()) {
            memberRepository.save(SignUpDto.toEntity(SignUpDto.encryptPassword(signUpDto)));
            return 0L;
        }else{
            throw new SignUpExceptionHandler(ExceptionCode.USER_ALREADY_EXIST, ExceptionCode.USER_ALREADY_EXIST.getDescription());
        }
    }
}