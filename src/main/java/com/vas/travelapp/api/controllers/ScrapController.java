package com.vas.travelapp.api.controllers;

import com.vas.travelapp.api.dtos.ScrapDto;
import com.vas.travelapp.api.mappers.ScrapMapper;
import com.vas.travelapp.domain.scrap.ScrapService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/public")
@RequiredArgsConstructor
public class ScrapController {

    private final ScrapService service;
    private final ScrapMapper mapper;

    @PostMapping("/")
    public void route(ScrapDto scrapDto) {
        service.saveOrUpdate(mapper.toScrap(scrapDto));
    }
}
