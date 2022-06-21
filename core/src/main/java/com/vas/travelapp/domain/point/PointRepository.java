package com.vas.travelapp.domain.point;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@SuppressWarnings("java:S107")
public interface PointRepository extends JpaRepository<Point, Long> {
    @Query(nativeQuery = true, value = "select * from points order by random() limit 1")
    Point findAny();

    @Query(value = "select * " +
            "from points p " +
            "join operation_hours oh on p.id = oh.point_id and oh.day_of_week = :pDayOfWeek " +
            "join addresses ad on p.address_id = ad.id " +
            "where p.type in (:pTypes) " +
            "and :pTime between oh.opening_time and oh.close_time " +
            "and :pPrice >= p.price " +
            "and ST_Distance(ST_SetSRID(geography(ST_MakePoint(:pLongitude, :pLatitude)), 4326), " +
            "ST_SetSRID(geography(ST_MakePoint(ad.longitude, ad.latitude)), 4326)) <= :pDistance " +
            "and p.id not in (:pBlacklist) " +
            "order by random() " +
            "limit 1", nativeQuery = true)
    Point find(@Param("pTime") Timestamp time, @Param("pPrice") int price, @Param("pDayOfWeek") int dayOfWeek,
               @Param("pTypes") Set<Integer> types,
               @Param("pLongitude") double longitude, @Param("pLatitude") double latitude,
               @Param("pDistance") int distance, @Param("pBlacklist") Set<Long> blacklist);


    @Query(value = "select count(*) " +
            "from points p " +
            "join operation_hours oh on p.id = oh.point_id and oh.day_of_week = :pDayOfWeek " +
            "join addresses ad on p.address_id = ad.id " +
            "where p.type in (:pTypes) " +
            "and :pTime between oh.opening_time and oh.close_time " +
            "and :pPrice >= p.price " +
            "and ST_Distance(ST_SetSRID(geography(ST_MakePoint(:pLongitude, :pLatitude)), 4326), " +
            "ST_SetSRID(geography(ST_MakePoint(ad.longitude, ad.latitude)), 4326)) <= :pDistance " +
            "order by random() " +
            "limit 1", nativeQuery = true)
    Long count(@Param("pTime") Timestamp time, @Param("pPrice") int price, @Param("pDayOfWeek") int dayOfWeek,
               @Param("pTypes") Set<Integer> types,
               @Param("pLongitude") double longitude, @Param("pLatitude") double latitude,
               @Param("pDistance") int distance);

    @Query(nativeQuery = true,
            value = "select * " +
                    "from points p " +
                    "join operation_hours oh on p.id = oh.point_id and oh.day_of_week = :pDayOfWeek " +
                    "join addresses ad on p.address_id = ad.id " +
                    "where p.type in (:pTypes) " +
                    "and :pTime between oh.opening_time and oh.close_time " +
                    "and p.price in (:pPrices) " +
                    "and ST_Distance(ST_SetSRID(geography(ST_MakePoint(:pLongitude, :pLatitude)), 4326), " +
                    "ST_SetSRID(geography(ST_MakePoint(ad.longitude, ad.latitude)), 4326)) <= :pDistance")
    List<Point> findPointsByRequestOpen(@Param("pTime") Timestamp time, @Param("pPrices") Set<Integer> prices, @Param("pDayOfWeek") int dayOfWeek,
                                        @Param("pTypes") Set<Integer> types,
                                        @Param("pLongitude") double longitude, @Param("pLatitude") double latitude,
                                        @Param("pDistance") int distance);

    @Query(nativeQuery = true,
            value = "select * " +
                    "from points p " +
                    "join operation_hours oh on p.id = oh.point_id " +
                    "join addresses ad on p.address_id = ad.id " +
                    "where p.type in (:pTypes) " +
                    "and p.price in (:pPrices) " +
                    "and ST_Distance(ST_SetSRID(geography(ST_MakePoint(:pLongitude, :pLatitude)), 4326), " +
                    "ST_SetSRID(geography(ST_MakePoint(ad.longitude, ad.latitude)), 4326)) <= :pDistance")
    List<Point> findPointsByRequest(@Param("pPrices") Set<Integer> prices, @Param("pTypes") Set<Integer> types,
                                    @Param("pLongitude") double longitude, @Param("pLatitude") double latitude,
                                    @Param("pDistance") int distance);
}
