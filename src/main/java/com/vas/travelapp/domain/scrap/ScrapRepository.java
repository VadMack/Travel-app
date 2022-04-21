package com.vas.travelapp.domain.scrap;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ScrapRepository extends MongoRepository<Scrap, Long> {
}
