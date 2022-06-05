package com.vas.travelapp.api.controllers;

import com.vas.travelapp.api.dtos.PointDto;
import com.vas.travelapp.api.mappers.PointMapper;
import com.vas.travelapp.domain.point.Point;
import com.vas.travelapp.domain.point.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
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

    @GetMapping
    public List<PointDto> createOrUpdate(@RequestBody @Valid Pageable pageable) {
        return pointMapper.toDtos(pointService.findAll(pageable));
    }
}
