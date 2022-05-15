package com.vas.travelapp.api.controllers;

import com.vas.travelapp.domain.point.Point;
import com.vas.travelapp.domain.scrap.ScrapDto;
import com.vas.travelapp.service.ScrapService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/private/scrap")
@RequiredArgsConstructor
public class ScrapController {

    private final ScrapService scrapService;

    @PostMapping("/add")
    public Point addPointFromScrap(
            @RequestBody @Valid ScrapDto dto
    ) {
        return scrapService.saveScrap(dto);
    }
}