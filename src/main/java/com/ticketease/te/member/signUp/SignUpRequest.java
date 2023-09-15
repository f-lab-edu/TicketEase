package com.ticketease.te.member.signUp;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Pattern;

public record SignUpRequest(
	@Length(min = 5, message = "id Error")
	String nickname,
	@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "pw complexity Error")
	String password,
	String confirmPassword
) {
	public static SignUpRequest of(String nickname, String password, String confirmPassword) {
		return new SignUpRequest(nickname, password, confirmPassword);
	}

	public static SignUpDto toDto(SignUpRequest request) {
		return SignUpDto.of(request.nickname, request.password, request.confirmPassword);
	}
}
