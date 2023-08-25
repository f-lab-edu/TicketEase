package com.TicketEase.TE.Exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiException extends RuntimeException {
    private final ErrorCode errorCode;
    private final String message;

    public ApiException(final ErrorCode errorCode, final String message) {
        this.errorCode = errorCode;
        this.message = message;
    }
    public HttpStatus getHttpStatus() {
        return this.errorCode.getHttpStatus();
    }
}
