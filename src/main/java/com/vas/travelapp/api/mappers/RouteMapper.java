package com.vas.travelapp.api.mappers;

import com.vas.travelapp.api.dtos.RouteDto;
import com.vas.travelapp.domain.route.Route;

import java.util.List;
import java.util.stream.Collectors;

public class RouteMapper {
    // todo: implement
    public RouteDto toDto(Route route) {
        if (route == null) {
            return null;
        }

        RouteDto routeDto = new RouteDto();
        routeDto.setId(route.getId());
        return routeDto;
    }

    public List<RouteDto> toDto(List<Route> routes) {
        if (routes == null) {
            return null;
        }

        return routes.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
