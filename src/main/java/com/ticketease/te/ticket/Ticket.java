package com.ticketease.te.ticket;

import jakarta.persistence.*;
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

    private Long performanceId;
    private Long memberTicketId;

    public void setPerformance(Long performanceId) {
        this.performanceId = performanceId;
    }

    private Ticket(final int fixedPrice, final Integer seatCount, final Grade grade) {
        this.seat = new Seat(seatCount, grade);
        this.fixedPrice = fixedPrice;
    }

    public void reserve(int count) {
        seat = seat.discountSeat(count);
    }

    public static Ticket of(final int fixedPrice, final Integer seatCount, final Grade grade) {
        return new Ticket(fixedPrice, seatCount, grade);
    }


    @Getter
    @Embeddable
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Seat {

        private Integer seatCount;

        @Enumerated(value = EnumType.STRING)
        private Grade grade;

        private Seat(Integer seatCount, Grade grade) {
            this.seatCount = seatCount;
            this.grade = grade;
        }

        private Seat discountSeat(final Integer requestCount) {
            if (this.seatCount < requestCount) {
                throw new RuntimeException("좌석이 부족합니다.");
            }
            return new Seat(this.seatCount - requestCount, this.grade);
        }
    }
}