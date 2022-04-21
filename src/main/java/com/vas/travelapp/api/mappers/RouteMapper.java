package com.vas.travelapp.api.mappers;

import com.vas.travelapp.api.dtos.RouteDto;
import com.vas.travelapp.domain.route.Route;

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
}
