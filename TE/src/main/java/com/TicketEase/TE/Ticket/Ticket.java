package com.TicketEase.TE.Ticket;

import com.TicketEase.TE.Performance.Performance;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Getter
@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;
    @Enumerated(EnumType.STRING)
    private TicketGrade grade;
    private int fixedPrice;
    private int seatCount;
    @ManyToOne
    @JoinColumn(name = "performance_id")
    private Performance performance;
    protected Ticket(){
    }

    private Ticket(final TicketGrade grade, final int fixedPrice, final int seatCount, final Performance performance){
        this.grade = grade;
        this.fixedPrice = fixedPrice;
        this.seatCount = seatCount;
        this.performance = performance;
    }
    public static Ticket of(final TicketGrade grade, final int fixedPrice, final int seatCount, final Performance performance){
        return new Ticket(grade, fixedPrice, seatCount, performance);
    }
}
