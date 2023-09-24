package com.ticketease.te.orchestration;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class OrchestrationController {

	private final OrchestrationService orchestrationService;

	@PostMapping("/api/purchase-ticket")
	public ResponseEntity<PurchaseTicketResponse> purchaseTicket(PurchaseTicketRequest request){
		orchestrationService.PurchaseTicket(request.nickname(), request.ticketId(), request.requestSeatCount());
		return ResponseEntity.status(HttpStatus.OK).body(new PurchaseTicketResponse("구매에 성공했습니다."));
	}
}
