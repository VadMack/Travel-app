package com.vas.travelapp.domain.route;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface RouteRepository extends MongoRepository<Route, Long> {

    // todo захуярить оконку со смещением и первичной выборкой по рандому чтобы получать список мест
    @Query(value = "select * from routes where origin = ?0 and destination = ?1 order by rand()")
    List<Route> findRoute();
}
