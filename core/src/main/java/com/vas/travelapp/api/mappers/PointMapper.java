package com.vas.travelapp.api.mappers;

import com.vas.travelapp.api.dtos.PointDto;
import com.vas.travelapp.domain.point.Point;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PointMapper {
    public List<PointDto> toDtos(List<Point> points) {
        return points.stream().map(this::toDto).toList();
    }

    private PointDto toDto(Point point) {
        if (point == null) {
            return null;
        }

        PointDto pointDto = new PointDto();

        pointDto.setName(point.getName());
        pointDto.setAddress(point.getAddress().getStreetAddress() + ", " + point.getAddress().getCity());

        return pointDto;
    }
}
