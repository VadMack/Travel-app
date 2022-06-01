package com.vas.travelapp.api.controllers;

import com.vas.travelapp.api.dtos.ScrapDto;
import com.vas.travelapp.api.mappers.ScrapMapper;
import com.vas.travelapp.domain.point.Point;
import com.vas.travelapp.domain.point.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/point")
@RequiredArgsConstructor
public class ScrapController {

    private final PointService pointService;
    private final ScrapMapper scrapMapper;

    @PostMapping
    public Point createOrUpdate(@RequestBody @Valid ScrapDto.ScrapDtoDetails dto) {
        return pointService.save(scrapMapper.scrapDtoToPoint(dto));
    }
}
