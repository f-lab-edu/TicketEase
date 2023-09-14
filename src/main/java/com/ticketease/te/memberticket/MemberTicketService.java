package com.ticketease.te.memberticket;

import com.ticketease.te.member.Member;
import com.ticketease.te.ticket.Ticket;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberTicketService {
    private final MemberTicketRepository memberTicketRepository;

    public void registerTicketForMember(Member member, Ticket ticket, Integer requestSeatCount){
        Boolean isCanceled = false;
        MemberTicket memberTicket = MemberTicket.of(member.getId(), ticket.getId(),
                ticket.getFixedPrice(), requestSeatCount, isCanceled);
        memberTicketRepository.save(memberTicket);
    }

    @Transactional
    public void cancelTicket(final Long memberTicketId){
        // 현재 날짜와 공연 날짜를 비교 하고 환불 할 수있는 지 확인 하는 로직 mt -> t -> p

        // 데드락 발생할 수 있으므로 account ticket memberTicket 순으로 로직 처리


    }
}
