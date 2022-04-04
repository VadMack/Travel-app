package com.vas.travelapp.repository;

import com.vas.travelapp.repository.model.PointModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PointRepository extends MongoRepository<PointModel, Long> {
    Optional<PointModel> findById(Long id);
}
