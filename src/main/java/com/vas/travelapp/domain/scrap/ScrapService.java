package com.vas.travelapp.domain.scrap;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScrapService {

    private final ScrapRepository scrapRepository;

    public void saveOrUpdate(Scrap scrap) {
        scrapRepository.save(scrap);
    }
}
