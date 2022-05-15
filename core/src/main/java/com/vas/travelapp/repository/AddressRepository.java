package com.vas.travelapp.repository;


import com.vas.travelapp.domain.point.Address;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface AddressRepository extends MongoRepository<Address, Long> {
    @Query("{'countryName' : ?0, 'cityName' : ?1, 'streetName' : ?2, 'streetNumber' : ?3}")
    Long countBy(String countryName, String cityName, String streetName, String streetNumber);

    @Query("{'countryName' : ?0, 'cityName' : ?1, 'streetName' : ?2, 'streetNumber' : ?3}")
    Address getBy(String countryName, String cityName, String streetName, String streetNumber);


}
