package com.vas.travelapp.domain.point;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface PointRepository extends MongoRepository<Point, Long> {
}
