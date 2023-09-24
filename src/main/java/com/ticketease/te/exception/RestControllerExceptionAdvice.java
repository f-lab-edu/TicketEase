package com.ticketease.te.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestControllerExceptionAdvice {

	@ExceptionHandler(com.ticketease.te.exception.ExceptionHandler.class)
	public ResponseEntity<Map<String, Object>> restControllerExceptionAdvice(
		com.ticketease.te.exception.ExceptionHandler ex) {
		Map<String, Object> responseBody = new HashMap<>();
		responseBody.put("name", ex.getCode());
		responseBody.put("description", ex.getCode().getDescription());
		//INFO : HttpStatus가 하드코딩으로 박혀있음
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);

	}

}
