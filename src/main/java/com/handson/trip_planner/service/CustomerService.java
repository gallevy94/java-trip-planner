package com.handson.trip_planner.service;

import com.handson.trip_planner.model.Customer;
import com.handson.trip_planner.repo.CustomerRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository repository;

    public Iterable<Customer> all() {return repository.findAll();}

    public Optional<Customer> findById(Long id) {return repository.findById(id);}

    public Customer save(Customer customer) {return repository.save(customer);}

    public void delete(Customer customer) {repository.delete(customer);}
}
