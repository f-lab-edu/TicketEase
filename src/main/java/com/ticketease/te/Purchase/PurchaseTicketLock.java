package com.ticketease.te.Purchase;

import java.util.concurrent.locks.ReentrantLock;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PurchaseTicketLock {
	private ReentrantLock lock = new ReentrantLock(true);
	private final PurchaseFacadeService purchaseFacadeService;

	public void purchaseInOrder(final String nickName, final Long ticketId,
		final Integer requestSeatCount) {
		lock.lock();
		try {
			purchaseFacadeService.purchaseTicket(nickName, ticketId, requestSeatCount);
		} finally {
			lock.unlock();
		}
	}
}
