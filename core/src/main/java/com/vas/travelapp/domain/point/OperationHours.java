package com.vas.travelapp.domain.point;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Objects;
import java.util.UUID;

import static javax.persistence.GenerationType.AUTO;

@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "operation_hours")
@Table(name = "operation_hours", schema = "public")
public class OperationHours {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private DayOfWeek dayOfWeek;
    private LocalTime openingTime;
    private LocalTime closeTime;
    private boolean closed;
    @ManyToOne
    @JoinColumn(name = "point_id")
    private Point point;


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
