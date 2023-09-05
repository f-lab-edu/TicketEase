package com.ticketease.te.performance;

import jakarta.persistence.Embeddable;
import java.time.LocalDateTime;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
public class PerformanceDateTime {
    private LocalDateTime performanceStartDateTime;
    private LocalDateTime performanceEndDateTime;

    public PerformanceDateTime(final LocalDateTime performanceStartDateTime,
                               final LocalDateTime performanceEndDateTime) {
        this.performanceStartDateTime = performanceStartDateTime;
        this.performanceEndDateTime = performanceEndDateTime;
    }

    public static PerformanceDateTime of(final LocalDateTime performanceStartDateTime, final LocalDateTime performanceEndDateTime){
        return new PerformanceDateTime(performanceStartDateTime, performanceEndDateTime);
    }
}