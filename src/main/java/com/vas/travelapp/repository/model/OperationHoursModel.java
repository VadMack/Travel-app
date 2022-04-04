package com.vas.travelapp.repository.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.*;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
@Document("opHours")
public class OperationHoursModel {
    @Id
    private Long id;

    private DayOfWeek dayOfWeek;
    private LocalTime openingTime;
    private LocalTime closeTime;
}
