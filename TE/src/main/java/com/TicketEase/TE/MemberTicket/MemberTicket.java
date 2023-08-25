package com.TicketEase.TE.MemberTicket;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
@Entity
public class MemberTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;
    private Long memberId;
    private Long accountId;
    private int realPrice;

    protected MemberTicket(){
    }

    private MemberTicket(final Long memberId, final Long accountId, final int realPrice) {
        this.memberId = memberId;
        this.accountId = accountId;
        this.realPrice = realPrice;
    }

    public static MemberTicket of(final Long memberId, final Long accountId, final int realPrice){
        return new MemberTicket(memberId, accountId, realPrice);
    }
}
