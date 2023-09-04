package com.ticketease.te.member.signUp;

import com.ticketease.te.member.Member;

public record SignUpDto (
        String nickName,
        String password,
        String confirmPassword
){
    public static SignUpDto of(String nickName, String password, String confirmPassword){
        return new SignUpDto(nickName, password, confirmPassword);
    }

    public static Member toEntity(SignUpDto dto){
        return Member.of(dto.nickName, dto.password);
    }
}
