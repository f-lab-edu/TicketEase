package com.ticketease.te.Purchase;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PurchaseTicketLock {
	private final Lockable lock;
	private final PurchaseFacadeService purchaseFacadeService;

	public void purchaseInOrder(final String nickName, final Long ticketId,
		final Integer requestSeatCount) {
		lock.lock(ticketId, () -> purchaseFacadeService.purchaseTicket(nickName, ticketId, requestSeatCount));
	}
}
