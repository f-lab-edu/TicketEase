package com.ticketease.te.account;

import com.ticketease.te.ticket.Ticket;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;

    public void deductAmount(Account account, Ticket ticket, Integer requestSeatCount){
        Integer totalPaymentAmount = ticket.getFixedPrice() * requestSeatCount;
        account.deductAmount(totalPaymentAmount);
        accountRepository.save(account);
    }
}
