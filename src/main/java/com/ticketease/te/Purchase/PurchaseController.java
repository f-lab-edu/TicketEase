package com.ticketease.te.Purchase;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class PurchaseController {
	private final PurchaseFacadeService purchaseFacadeService;

	@PostMapping("/api/tickets/ticketReserve")
	public ResponseEntity<String> reserveTicket(PurchaseRequest purchaseRequest) {

		purchaseFacadeService.purchaseTicket
			(
				purchaseRequest.nickName(),
				purchaseRequest.ticketId(),
				purchaseRequest.requestSeatCount()
			);
		return ResponseEntity.noContent().build();
	}
}
