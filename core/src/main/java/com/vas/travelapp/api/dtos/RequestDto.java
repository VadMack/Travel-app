package com.vas.travelapp.api.dtos;

import com.vas.travelapp.domain.point.enums.PointType;
import com.vas.travelapp.domain.point.enums.Price;
import lombok.Data;

import java.util.List;

@Data
public class RequestDto {
    List<PointType> types;
    List<Price> prices;
    Boolean open;
    Double latitude;
    Double longitude;
}
