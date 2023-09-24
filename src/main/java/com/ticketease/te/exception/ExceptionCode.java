package com.ticketease.te.exception;

import lombok.Getter;

@Getter
public enum ExceptionCode {
	USER_ALREADY_EXIST("이미 존재하는 Username 입니다."),
	INVALID_SIGNUP_REQUEST("비정상적인 요청입니다."),
	PERFORMANCE_DETAIL_NOT_FOUND("공연정보를 찾을 수 없습니다."),
	MEMBER_NOT_FOUND("유저를 찾을 수 없습니다."),
	TICKET_NOT_FOUND("티켓을 찾을 수 없습니다."),
	ACCOUNT_NOT_FOUND("계좌를 찾을 수 없습니다."),
	LACK_OF_TICKET_SEAT("잔여 좌석이 부족합니다."),
	LACK_OF_MONEY("계좌의 잔액이 부족합니다.");

	private final String description;

	ExceptionCode(String description) {
		this.description = description;
	}
}
