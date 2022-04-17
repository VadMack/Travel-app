package com.vas.travelapp.domain.point;

import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Objects;

@Getter
@Setter
@ToString
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Document("opHours")
@TypeAlias("opHour")
public class OperationHours {
    @Id
    private Long id;

    private DayOfWeek dayOfWeek;
    private LocalTime openingTime;
    private LocalTime closeTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OperationHours that = (OperationHours) o;
        return dayOfWeek == that.dayOfWeek
                && Objects.equals(openingTime, that.openingTime)
                && Objects.equals(closeTime, that.closeTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dayOfWeek, openingTime, closeTime);
    }
}
