package com.ticketease.te.memberticket;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.criteria.CriteriaBuilder.In;
import lombok.Getter;

@Getter
@Entity
public class MemberTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long memberId;
    private Long ticketId;
    private Integer realPrice;
    private Integer seatCount;
    private Boolean isCanceled;
    protected MemberTicket(){}

    public MemberTicket(final Long memberId, final Long ticketId,
                        final Integer realPrice, final Integer seatCount,
                        final Boolean isCanceled) {
        this.memberId = memberId;
        this.ticketId = ticketId;
        this.realPrice = realPrice;
        this.seatCount = seatCount;
        this.isCanceled = isCanceled;
    }

    public static MemberTicket of(final Long memberId, final Long ticketId,
                                  final Integer realPrice, final Integer seatCount, final Boolean isCanceled) {
        return new MemberTicket(memberId, ticketId, realPrice, seatCount, isCanceled);
    }

    public void cancel(){
        this.isCanceled = true;
    }
}
