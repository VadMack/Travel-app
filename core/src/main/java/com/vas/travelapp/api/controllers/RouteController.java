package com.vas.travelapp.api.controllers;

import com.vas.travelapp.api.dtos.PreferencesDto;
import com.vas.travelapp.api.dtos.RouteDto;
import com.vas.travelapp.api.mappers.PreferencesMapper;
import com.vas.travelapp.api.mappers.RouteMapper;
import com.vas.travelapp.domain.point.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/private/routes")
@RequiredArgsConstructor
public class RouteController {

    private final PointService service;
    private final RouteMapper routeMapper;
    private final PreferencesMapper preferencesMapper;

    @GetMapping(produces = "application/json", consumes = "application/json")
    public List<RouteDto> getRoute(@RequestBody PreferencesDto preferencesDto) {
        return routeMapper.toRoute(
                service.generateRoute(
                        preferencesMapper.toPreference(preferencesDto)));
    }

    /*@PostMapping
    public void generateRoute() {
        service.generateData();

    }*/
}
