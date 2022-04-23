package com.vas.travelapp.api.controllers;

import com.vas.travelapp.api.dtos.RouteDto;
import com.vas.travelapp.api.mappers.RouteMapper;
import com.vas.travelapp.domain.route.RouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/private/routes")
@RequiredArgsConstructor
public class RouteController {

    private final RouteService routeService;
    private final RouteMapper routeMapper;

    @GetMapping(value = "/", produces = "application/json")
    public List<RouteDto> route() {
        return routeMapper.toDto(routeService.getRoute());
    }

}
