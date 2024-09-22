package com.handson.trip_planner.repo;


import com.handson.trip_planner.model.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TripRepository extends JpaRepository<Trip, Long> {
    @Query("SELECT t FROM Trip t WHERE t.location = :location AND t.startDate = :startDate AND t.endDate = :endDate")
    Optional<Trip> findTrip(@Param("location") String location,
                            @Param("startDate") String startDate,
                            @Param("endDate") String endDate);

    @Query("SELECT t FROM Trip t WHERE t.dbUser.id = :user_id")
    List<Trip> findAllTripsByUserId(@Param("user_id") Long user_id);
}

