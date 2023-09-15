package com.ticketease.te.ticket;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ticketease.te.account.Account;
import com.ticketease.te.account.AccountRepository;
import com.ticketease.te.account.AccountService;
import com.ticketease.te.member.Member;
import com.ticketease.te.member.MemberRepository;
import com.ticketease.te.memberticket.MemberTicket;
import com.ticketease.te.memberticket.MemberTicketRepository;
import com.ticketease.te.memberticket.MemberTicketService;
import com.ticketease.te.performance.PerformanceService;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class CancelTicket {
    @InjectMocks
    private TicketService ticketService;

    @Mock
    private AccountService accountService;

    @Mock
    private MemberTicketService memberTicketService;

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private PerformanceService performanceService;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private MemberTicketRepository memberTicketRepository;

    @Mock
    private AccountRepository accountRepository;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void cancelTicketTest(){
       String nickName = "Ko";
       Long ticketId = 1L;
       Integer requestSeatCount = 2;
       Long memberTicketId = 1L;

        Member mockMember = mock(Member.class);
        Ticket mockTicket = mock(Ticket.class);
        Account mockAccount = mock(Account.class);
        Seat mockSeat = mock(Seat.class);
        MemberTicket mockMemberTicket = mock(MemberTicket.class);

        when(memberRepository.findByNickName(nickName)).thenReturn(Optional.of(mockMember));
        when(ticketRepository.findById(ticketId)).thenReturn(Optional.of(mockTicket));
        when(accountRepository.findById(anyLong())).thenReturn(Optional.of(mockAccount));
        when(memberTicketRepository.findById(memberTicketId)).thenReturn(Optional.of(mockMemberTicket));
        when(mockTicket.getSeat()).thenReturn(mockSeat);

        ticketService.cancelTicket(nickName, ticketId, requestSeatCount, memberTicketId);

        verify(performanceService, times(1)).isCanceled(mockTicket);
        verify(memberTicketService, times(1)).cancelMemberTicket(mockMemberTicket, mockAccount);
        verify(mockSeat, times(1)).reserveSeat(requestSeatCount);
    }
}
