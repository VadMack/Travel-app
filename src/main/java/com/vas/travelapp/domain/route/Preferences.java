package com.vas.travelapp.domain.route;

import com.vas.travelapp.api.dtos.TypeActivity;
import com.vas.travelapp.domain.point.enums.Price;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class Preferences {
    Timestamp currentTime;
    Integer minutes;
    TypeActivity activity;
    Price price;
    Integer distance;
    Coordinates coordinates;

    @Data
    @AllArgsConstructor
    public static class Coordinates {
        double longitude;
        double latitude;
    }
}
