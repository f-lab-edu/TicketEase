package com.ticketease.te.memberticket;

import com.ticketease.te.member.Member;
import com.ticketease.te.ticket.Ticket;
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
}
