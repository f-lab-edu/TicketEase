package com.ticketease.te.purchaseticket;

public record PurchaseTicketRequest(
	String nickname,
	Long ticketId,
	Integer requestSeatCount
) {
}
