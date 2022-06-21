package com.vas.travelapp.api.controllers;

import com.vas.travelapp.api.dtos.PointDto;
import com.vas.travelapp.api.dtos.RequestDto;
import com.vas.travelapp.api.mappers.PointMapper;
import com.vas.travelapp.domain.point.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/api/private/points")
@RequiredArgsConstructor
public class PointController {

    private final PointService pointService;
    private final PointMapper pointMapper;

    @GetMapping(produces = "application/json")
    public List<PointDto> createOrUpdate(@RequestBody @Valid RequestDto requestDto) {
        return pointMapper.toDtos(pointService.findAll(requestDto));
    }
}
