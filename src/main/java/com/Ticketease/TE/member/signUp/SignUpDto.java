package com.Ticketease.TE.member.signUp;

import com.Ticketease.TE.member.Member;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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

    public static SignUpDto encryptPassword(SignUpDto dto){
        final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(4);
        return new SignUpDto(dto.nickName, encoder.encode(dto.password), dto.confirmPassword);
    }
}
