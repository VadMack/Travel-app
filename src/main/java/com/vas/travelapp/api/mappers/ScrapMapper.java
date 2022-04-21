package com.vas.travelapp.api.mappers;

import com.vas.travelapp.api.dtos.ScrapDto;
import com.vas.travelapp.domain.scrap.Scrap;
import org.springframework.stereotype.Component;


@Component
public class ScrapMapper {
    public Scrap toScrap(ScrapDto scrapDto) {
        if (scrapDto == null) {
            return null;
        }

        Scrap scrap = new Scrap();
        scrap.setId(scrapDto.getId());
        return scrap;
    }
}
