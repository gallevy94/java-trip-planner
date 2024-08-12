package com.handson.trip_planner.repo;


import com.handson.trip_planner.model.CustomerTrip;
import org.springframework.data.repository.CrudRepository;

public interface CustomerTripRepository extends CrudRepository<CustomerTrip, Long> {
}

