<<<<<<< HEAD
package com.ticketease.te.performance;

import jakarta.persistence.Embeddable;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor
public class PerformanceDateTime {
    private LocalDateTime performanceStartDateTime;
    private LocalDateTime performanceEndDateTimeTime;

    public PerformanceDateTime(final LocalDateTime performanceStartDate,
                               final LocalDateTime performanceEndDateTime) {
        this.performanceStartDateTime = performanceStartDate;
        this.performanceEndDateTimeTime = performanceEndDateTime;
    }

    public static PerformanceDateTime of(final LocalDateTime performanceStartDate, final LocalDateTime performanceEndDateTime){
        return new PerformanceDateTime(performanceStartDate, performanceEndDateTime);
    }
}
=======
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
>>>>>>> 4b8cbb66209fb14f4d97411d810a840771db0d63
