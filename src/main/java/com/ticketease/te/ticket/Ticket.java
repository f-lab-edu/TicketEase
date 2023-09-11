<<<<<<< HEAD
package com.ticketease.te.ticket;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;
    private int seatCount;
    private int fixedPrice;
    @Enumerated(value = EnumType.STRING)
    private Grade grade;

    private Long performanceId;
    public void setPerformance(Long performanceId){
        this.performanceId = performanceId;
    }
    protected Ticket(){}

    private Ticket(final int seatCount, final int fixedPrice,
                   final Grade grade) {
        this.seatCount = seatCount;
        this.fixedPrice = fixedPrice;
        this.grade = grade;
    }
    public static Ticket of(final int seatCount, final int fixedPrice,
                            final Grade grade){
        return new Ticket(seatCount, fixedPrice, grade);
    }
}
=======
package com.ticketease.te.ticket;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;
    @Embedded
    private Seat seat;
    private Integer fixedPrice;
    @Enumerated(value = EnumType.STRING)
    private Grade grade;

    private Long performanceId;
    private Long memberTicketId;
    public void setPerformance(Long performanceId){
        this.performanceId = performanceId;
    }

    private Ticket(final Seat seat, final int fixedPrice,
                   final Grade grade) {
        this.seat = seat;
        this.fixedPrice = fixedPrice;
        this.grade = grade;
    }
    public static Ticket of(final Seat seat, final Integer fixedPrice,
                            final Grade grade){
        return new Ticket(seat, fixedPrice, grade);
    }
}
>>>>>>> 4b8cbb66209fb14f4d97411d810a840771db0d63
