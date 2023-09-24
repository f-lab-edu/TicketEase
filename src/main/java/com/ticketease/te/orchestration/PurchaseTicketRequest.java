package com.ticketease.te.orchestration;

public record PurchaseTicketRequest(
	String nickname,
	Long ticketId,
	Integer requestSeatCount
) {
}
