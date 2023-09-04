package com.Ticketease.TE.member.Login;

public record LoginRequest(
        String nickname,
        String password
) {

    public static LoginRequest of(String nickname, String password){
        return new LoginRequest(nickname,password);
    }
}
