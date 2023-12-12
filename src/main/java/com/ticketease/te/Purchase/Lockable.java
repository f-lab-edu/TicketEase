package com.ticketease.te.Purchase;

@FunctionalInterface
public interface Lockable {
	void lock(Long ticketId, Runnable runnable);
}
