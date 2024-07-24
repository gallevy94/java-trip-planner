package com.handson.trip_planner.repo;

import com.handson.trip_planner.model.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
}
