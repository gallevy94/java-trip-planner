package com.handson.trip_planner.repo;


import com.handson.trip_planner.model.Trip;
import org.springframework.data.repository.CrudRepository;

public interface TripRepository extends CrudRepository<Trip, Long> {
}

