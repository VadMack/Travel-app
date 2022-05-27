package com.vas.travelapp.api.mappers;

import com.vas.travelapp.api.dtos.RouteDto;
import com.vas.travelapp.domain.point.Point;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class RouteMapper {
    public RouteDto toRoute(Point point) {
        if (point == null) {
            return null;
        }

        RouteDto routeDto = new RouteDto();
        routeDto.setId(point.getId());
        routeDto.setName(point.getName());
        routeDto.setType(point.getType());
        routeDto.setPrice(point.getPrice());
        routeDto.setAddress(point.getAddress().toString());
        return routeDto;
    }

    public List<RouteDto> toRoute(Collection<Point> point) {
        return point.stream().map(this::toRoute).toList();
    }
}
