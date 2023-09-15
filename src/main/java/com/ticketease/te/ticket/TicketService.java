package com.ticketease.te.ticket;


import com.ticketease.te.account.Account;
import com.ticketease.te.account.AccountRepository;
import com.ticketease.te.account.AccountService;
import com.ticketease.te.member.Member;
import com.ticketease.te.member.MemberRepository;
import com.ticketease.te.memberticket.MemberTicket;
import com.ticketease.te.memberticket.MemberTicketRepository;
import com.ticketease.te.memberticket.MemberTicketService;
import com.ticketease.te.performance.Performance;
import com.ticketease.te.performance.PerformanceService;
import com.ticketease.te.util.EntityFinderUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TicketService{
    private final AccountService accountService;
    private final MemberTicketService memberTicketService;
    private final TicketRepository ticketRepository;
    private final PerformanceService performanceService;
    private final MemberRepository memberRepository;
    private final MemberTicketRepository memberTicketRepository;
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
        Seat seat  = ticket.getSeat();

        accountService.deductAmount(account, ticket, requestSeatCount);

        seat.reserveSeat(requestSeatCount);
        ticketRepository.save(ticket);

        memberTicketService.registerTicketForMember(member, ticket, requestSeatCount);
    }

    @Transactional
    public synchronized void cancelTicket(final String nickName, final Long ticketId,
                                          final Integer requestSeatCount,final Long memberTicketId){
        Member member = memberRepository.findByNickName(nickName)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 이름입니다 닉네임 : " + nickName));
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 상품입니다 상품Id : " + ticketId));
        Account account = accountRepository.findById(member.getAccountId())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 계좌입니다"));

        MemberTicket memberTicket = memberTicketRepository.findById(memberTicketId)
                .orElseThrow(() -> new RuntimeException("존재하지 않은 사용자 티켓입니다"));
        Seat seat  = ticket.getSeat();
        // 현재 날짜와 공연 날짜를 비교 하고 환불 할 수있는 지 확인 하는 로직
        performanceService.isCanceled(ticket);

        //  기존 티켓 환불 로직
        memberTicketService.cancelMemberTicket(memberTicket, account);

        seat.addSeat(requestSeatCount);
        ticketRepository.save(ticket);

        // requestSeatCount  만큼 재 구매
        purchaseTicket(nickName, ticketId, requestSeatCount);
    }
}

