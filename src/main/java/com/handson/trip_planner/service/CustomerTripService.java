package com.handson.trip_planner.service;

import com.handson.trip_planner.model.CustomerTrip;
import com.handson.trip_planner.repo.CustomerTripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class CustomerTripService {

    @Autowired
    CustomerTripRepository repository;

    public Iterable<CustomerTrip> all() {
        return repository.findAll();
    }

    public Optional<CustomerTrip> findById(Long id) {
        return repository.findById(id);
    }


    public CustomerTrip save(CustomerTrip customerTrip) {
        return repository.save(customerTrip);
    }

    public void delete(CustomerTrip customerTrip) {
        repository.delete(customerTrip);
    }

}
