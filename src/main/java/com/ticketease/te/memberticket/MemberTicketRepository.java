package com.ticketease.te.memberticket;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberTicketRepository extends JpaRepository<MemberTicket, Long> {
}
