package com.ticketease.te.ticket;


import com.ticketease.te.account.Account;
import com.ticketease.te.account.AccountService;
import com.ticketease.te.member.Member;
import com.ticketease.te.memberticket.MemberTicketService;
import com.ticketease.te.performance.Performance;
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
    private final EntityFinderUtil entityFinderUtil;

    public GradeCount countTicketByGradeForPerformance(Performance performance) {
        return GradeCount.from(ticketRepository.findAllById(performance.getTicketIds()));
    }
    @Transactional
    public void purchaseTicket(final String nickName, final Long ticketId, final Integer requestSeatCount) {

        Member member = entityFinderUtil.findMemberByNickName(nickName);
        Ticket ticket = entityFinderUtil.findTicketById(ticketId);
        Account account = entityFinderUtil.findAccountByMember(member);
        Seat seat  = ticket.getSeat();

        accountService.deductAmount(account, ticket, requestSeatCount);

        seat.reserveSeat(requestSeatCount);
        ticketRepository.save(ticket);

        memberTicketService.registerTicketForMember(member, ticket, requestSeatCount);
    }

    @Transactional
    public void cancelTicket(final String nickName, final Long ticketId,
                             final Integer requestSeatCount,final Long memberTicketId){
        Member member = entityFinderUtil.findMemberByNickName(nickName);
        Ticket ticket = entityFinderUtil.findTicketById(ticketId);
        Account account = entityFinderUtil.findAccountByMember(member);
        Seat seat  = ticket.getSeat();
        // 현재 날짜와 공연 날짜를 비교 하고 환불 할 수있는 지 확인 하는 로직 mt -> t -> p

        // 데드락 발생할 수 있으므로 account ticket memberTicket 순으로 로직 처리

    }
}
