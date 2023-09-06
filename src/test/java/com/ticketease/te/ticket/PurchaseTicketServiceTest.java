package com.ticketease.te.ticket;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ticketease.te.account.Account;
import com.ticketease.te.account.AccountRepository;
import com.ticketease.te.member.Member;
import com.ticketease.te.member.MemberRepository;
import com.ticketease.te.memberticket.MemberTicket;
import com.ticketease.te.memberticket.MemberTicketRepository;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class PurchaseTicketServiceTest {

    @InjectMocks
    private TicketService ticketService;

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private MemberTicketRepository memberTicketRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("티켓 예매 성공")
    void purchaseTicket_success() {
        // Given
        final String nickName = "ko";
        final String password = "1234";
        final Long accountId = 1L;
        Member mockMember = Member.of(nickName, password);
        mockMember.addAccount(accountId);
        final Integer seatCount = 100;
        final Integer fixedPrice = 50000;
        Grade seatGrade = Grade.S;
        Ticket mockTicket = Ticket.of(seatCount, fixedPrice, seatGrade);
        final Long memberAccountAmount = 100000L;
        Account mockAccount = Account.of(memberAccountAmount);

        when(memberRepository.findByNickName(nickName)).thenReturn(Optional.of(mockMember));
        when(ticketRepository.findById(anyLong())).thenReturn(Optional.of(mockTicket));
        when(accountRepository.findById(anyLong())).thenReturn(Optional.of(mockAccount));
        final Long ticketId = 1L;
        final Integer requestSeatCount = 1;
        // When
        ticketService.purchaseTicket(nickName, ticketId, requestSeatCount);

        // Then
        verify(ticketRepository, times(1)).save(mockTicket);
        verify(accountRepository, times(1)).save(mockAccount);
        verify(memberTicketRepository, times(1)).save(any(MemberTicket.class));
    }

    @Test
    void purchaseTicket_notEnoughBalance_shouldThrowException() {
        final String nickName = "ko";
        final String password = "1234";
        final Integer seatCount = 100;
        final Integer fixedPrice = 50000;
        final Long memberAccountAmount = 40000L;
        Grade seatGrade = Grade.S;
        final Long ticketId = 1L;
        final Long accountId = 1L;
        final Integer requestSeatCount = 1;
        // Given
        Member mockMember = Member.of(nickName, password);
        mockMember.addAccount(accountId);
        Ticket mockTicket = Ticket.of(seatCount, fixedPrice, seatGrade);
        Account mockAccount = Account.of(memberAccountAmount);

        when(memberRepository.findByNickName(nickName)).thenReturn(Optional.of(mockMember));
        when(ticketRepository.findById(anyLong())).thenReturn(Optional.of(mockTicket));
        when(accountRepository.findById(anyLong())).thenReturn(Optional.of(mockAccount));

        // When & Then
        assertThrows(RuntimeException.class, () -> ticketService.purchaseTicket(nickName, ticketId, requestSeatCount));
    }
}