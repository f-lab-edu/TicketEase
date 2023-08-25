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
    private LocalDateTime showStartDate;
    private LocalDateTime showEndDate;

    protected Performance(){
    }

    private Performance(final String name, final String description, final LocalDateTime showStartDate,
                       final LocalDateTime showEndDate) {
        this.name = name;
        this.description = description;
        this.showStartDate = showStartDate;
        this.showEndDate = showEndDate;
    }

    public static Performance of(final String name, final String description, final LocalDateTime showStartDate,
                                 final LocalDateTime showEndDate){
        return new Performance(name, description, showStartDate, showEndDate);
    }
}
