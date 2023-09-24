package com.ticketease.te.ticket;

import org.springframework.stereotype.Service;

import com.ticketease.te.account.Account;
import com.ticketease.te.account.AccountRepository;
import com.ticketease.te.account.AccountService;
import com.ticketease.te.exception.ExceptionCode;
import com.ticketease.te.exception.ExceptionHandler;
import com.ticketease.te.member.Member;
import com.ticketease.te.member.MemberRepository;
import com.ticketease.te.memberticket.MemberTicketService;
import com.ticketease.te.performance.Performance;

import jakarta.transaction.Transactional;
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

	@Transactional
	public void purchaseTicket(final String nickName, final Long ticketId, final Integer requestSeatCount) {

		Member member = memberRepository.findByNickName(nickName)
			.orElseThrow(() -> new RuntimeException("존재하지 않는 이름입니다 닉네임 : " + nickName));
		Ticket ticket = ticketRepository.findById(ticketId)
			.orElseThrow(() -> new RuntimeException("존재하지 않는 상품입니다 상품Id : " + ticketId));
		Account account = accountRepository.findById(member.getAccountId())
			.orElseThrow(() -> new RuntimeException("존재하지 않는 계좌입니다"));
		Seat seat = ticket.getSeat();

		accountService.deductAmount(account, ticket, requestSeatCount);

		seat.reserveSeat(requestSeatCount);
		ticketRepository.save(ticket);

		memberTicketService.registerTicketForMember(member, ticket, requestSeatCount);
	}

	public Ticket ReserveSeat(Long ticketId, Integer count) {
		Ticket ticket = ticketRepository.findById(ticketId)
			.orElseThrow(() -> new ExceptionHandler(ExceptionCode.TICKET_NOT_FOUND,
				ExceptionCode.TICKET_NOT_FOUND.getDescription()));
		ticket.getSeat().reserveSeats(count);
		return ticket;
	}
}
