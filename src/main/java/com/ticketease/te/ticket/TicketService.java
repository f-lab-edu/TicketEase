package com.ticketease.te.ticket;


import com.ticketease.te.account.Account;
import com.ticketease.te.account.AccountRepository;
import com.ticketease.te.member.Member;
import com.ticketease.te.member.MemberRepository;
import com.ticketease.te.memberticket.MemberTicket;
import com.ticketease.te.memberticket.MemberTicketRepository;
import com.ticketease.te.performance.Performance;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TicketService{
    private final TicketRepository ticketRepository;
    private final MemberRepository memberRepository;
    private final AccountRepository accountRepository;
    private final MemberTicketRepository memberTicketRepository;

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
        if (account.getAmount() < ticket.getFixedPrice()){
            throw new RuntimeException("잔액이 부족합니다");
        }

        // 좌석의 계수 줄여주기
        ticket.calculateSeat(requestSeatCount);
        ticketRepository.save(ticket);

        account.calculate(ticket.getFixedPrice() * requestSeatCount);
        accountRepository.save(account);

        MemberTicket memberTicket = MemberTicket.of(member.getId(), ticketId, ticket.getFixedPrice(), requestSeatCount);
        memberTicketRepository.save(memberTicket);
    }
}
