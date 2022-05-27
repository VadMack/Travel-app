package com.vas.travelapp.api.dtos;

import com.vas.travelapp.domain.point.enums.PointType;
import com.vas.travelapp.domain.point.enums.Price;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RouteDto {
    private Long id;
    private String name;
    private PointType type;
    private Price price;
    private String address;
}
