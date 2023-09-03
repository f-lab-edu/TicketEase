package com.Ticketease.TE.exception;

import lombok.Getter;

@Getter
public class SignUpExceptionHandler extends RuntimeException{
    ExceptionCode code;
    String message;

    public SignUpExceptionHandler(ExceptionCode code, String message) {
        this.code = code;
        this.message = message;
    }
}
