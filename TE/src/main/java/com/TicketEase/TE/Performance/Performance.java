package com.TicketEase.TE.Performance;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
@Entity
public class Performance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;
    private String name;
    private String description;
    private LocalDateTime performanceStartDate;
    private LocalDateTime performanceEndDate;

    protected Performance(){
    }

    private Performance(final String name, final String description, final LocalDateTime performanceStartDate,
                       final LocalDateTime performanceEndDate) {
        this.name = name;
        this.description = description;
        this.performanceStartDate = performanceStartDate;
        this.performanceEndDate = performanceEndDate;
    }

    public static Performance of(final String name, final String description, final LocalDateTime performanceStartDate,
                                 final LocalDateTime performanceEndDate){
        return new Performance(name, description, performanceStartDate, performanceEndDate);
    }
}
