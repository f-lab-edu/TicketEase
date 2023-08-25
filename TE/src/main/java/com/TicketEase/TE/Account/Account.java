package com.TicketEase.TE.Account;

import com.TicketEase.TE.Member.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;

@Getter
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;
    private Long amount;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;
    protected Account(){}

    private Account(final Long amount, final Member member){
        this.amount = amount;
        this.member = member;
    }
    public static Account of(Long amount, Member member){
        return new Account(amount, member);
    }
}
