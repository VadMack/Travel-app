package com.vas.travelapp.repository;


import com.vas.travelapp.domain.point.Address;
import com.vas.travelapp.domain.point.Point;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface PointRepository extends MongoRepository<Point, Long> {
    @Query("{'name' : ?0, 'address' : ?1}")
    Long countBy(String name, Address address);

    @Query("{'name' : ?0, 'address' : ?1}")
    Point getBy(String name, Address address);
}
