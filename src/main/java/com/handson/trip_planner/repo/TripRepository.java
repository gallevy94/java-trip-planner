package com.handson.trip_planner.repo;


import com.handson.trip_planner.model.Trip;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TripRepository extends CrudRepository<Trip, Long> {
    @Query("SELECT t FROM Trip t WHERE t.location = :location AND t.startDate = :startDate AND t.endDate = :endDate")
    Optional<Trip> findTrip(@Param("location") String location,
                            @Param("startDate") String startDate,
                            @Param("endDate") String endDate);
}

