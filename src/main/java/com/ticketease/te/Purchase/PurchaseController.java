package com.ticketease.te.Purchase;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class PurchaseController {
	private final PurchaseFacadeService purchaseFacadeService;

	@PostMapping("/api/tickets/ticketReserve")
	public ResponseEntity<String> reserveTicket(@RequestParam String nickName, @RequestParam Long ticketId,
		@RequestParam Integer requestSeatCount) {
		try {
			purchaseFacadeService.purchaseTicket(nickName, ticketId, requestSeatCount);
			return ResponseEntity.ok("성공적으로 티켓 구매가 되었습니다.");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
