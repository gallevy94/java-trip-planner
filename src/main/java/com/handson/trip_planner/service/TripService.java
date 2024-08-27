package com.handson.trip_planner.service;

import com.handson.trip_planner.model.Trip;
import com.handson.trip_planner.repo.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class TripService {
    @Autowired
    TripRepository repository;

    public Iterable<Trip> all() {
        return repository.findAll();
    }

    public Optional<Trip> findById(Long id) {
        return repository.findById(id);
    }


    public Trip save(Trip trip) {
        return repository.save(trip);
    }

    public void delete(Trip trip) {
        repository.delete(trip);
    }

}
