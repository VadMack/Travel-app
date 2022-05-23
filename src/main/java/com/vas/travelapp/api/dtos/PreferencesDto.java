package com.vas.travelapp.api.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vas.travelapp.domain.point.enums.Price;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PreferencesDto {
    TypeActivity activity;
    Integer hours;
    Price price;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime dateTime;
    TransportType transport;
    Double latitude;
    Double longitude;
}
