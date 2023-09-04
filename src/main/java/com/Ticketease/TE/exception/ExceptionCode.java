package com.Ticketease.TE.exception;

import lombok.Getter;
@Getter
public enum ExceptionCode {
    USER_ALREADY_EXIST("이미 존재하는 Username 입니다."),
    INVALID_SIGNUP_REQUEST("비정상적인 요청입니다.");

    private final String description;

    ExceptionCode(String description) {
        this.description = description;
    }
}
