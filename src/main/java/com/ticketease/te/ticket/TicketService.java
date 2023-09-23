package com.ticketease.te.ticket;

import org.springframework.stereotype.Service;

import com.ticketease.te.account.AccountRepository;
import com.ticketease.te.account.AccountService;
import com.ticketease.te.member.MemberRepository;
import com.ticketease.te.memberticket.MemberTicketService;
import com.ticketease.te.performance.Performance;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TicketService {
	private final AccountService accountService;
	private final MemberTicketService memberTicketService;
	private final TicketRepository ticketRepository;
	private final MemberRepository memberRepository;
	private final AccountRepository accountRepository;

	public GradeCount countTicketByGradeForPerformance(Performance performance) {
		return GradeCount.from(ticketRepository.findAllById(performance.getTicketIds()));
	}

	public Ticket findTicketById(Long ticketId) {
		return ticketRepository.findById(ticketId)
			.orElseThrow(() -> new RuntimeException("존재하지 않는 상품입니다 상품Id : " + ticketId));
	}

	public void saveTicket(Ticket ticket) {
		ticketRepository.save(ticket);
	}
}
