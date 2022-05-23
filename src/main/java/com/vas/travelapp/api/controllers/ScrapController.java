package com.vas.travelapp.api.controllers;

import com.vas.travelapp.api.dtos.PointDto;
import com.vas.travelapp.api.mappers.PointMapper;
import com.vas.travelapp.domain.point.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/public")
@RequiredArgsConstructor
public class ScrapController {

    private final PointService service;
    private final PointMapper mapper;

    @PostMapping("/")
    public void route(PointDto scrapDto) {
        service.save(mapper.toPoint(scrapDto));
    }

    @PostMapping("/new")
    public void route() {
        service.save(mapper.toPoint(new PointDto("name", "description", "url", "imageUrl")));
    }
}
