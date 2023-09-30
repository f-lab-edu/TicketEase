package com.ticketease.te.ticket;

import com.ticketease.te.performance.Performance;

public interface TicketReader {
	Ticket findTicketBy(Long ticketId);

	Seat getSeat(Long ticketId);

	GradeCount countTicketByGradeFor(Performance performance);
}
