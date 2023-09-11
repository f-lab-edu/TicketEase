<<<<<<< HEAD
package com.ticketease.te.ticket;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByPerformanceId(Long performanceId);
}
=======
package com.ticketease.te.ticket;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
>>>>>>> 4b8cbb66209fb14f4d97411d810a840771db0d63
