package com.Ticketease.TE.member.signUp;

import com.Ticketease.TE.exception.ExceptionCode;
import com.Ticketease.TE.exception.SignUpExceptionHandler;
import com.Ticketease.TE.member.Member;
import com.Ticketease.TE.member.MemberRepository;
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