package com.handson.trip_planner.controller;

import com.handson.trip_planner.model.Customer;
import com.handson.trip_planner.model.CustomerTrip;
import com.handson.trip_planner.model.TripIn;
import com.handson.trip_planner.service.CustomerService;
import com.handson.trip_planner.service.CustomerTripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/customers")
public class CustomersTripsController {
    @Autowired
    CustomerService customerService;

    @Autowired
    CustomerTripService customerTripService;


    @RequestMapping(value = "/{customerId}/trips", method = RequestMethod.POST)
    public ResponseEntity<?> insertCustomerTrip(Long customerId, @RequestBody TripIn tripIn)
    {
        var customer = customerService.findById(customerId);
        if (customer.isEmpty()) throw new RuntimeException("Customer:" + customerId +" not found");
        CustomerTrip customerTrip = tripIn.toTrip(customer.get());
        customerTrip = customerTripService.save(customerTrip);
        return new ResponseEntity<>(customerTrip, HttpStatus.OK);
    }

    @RequestMapping(value = "/{customerId}/trips/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateCustomer(@PathVariable Long customerId, @PathVariable Long id, @RequestBody TripIn tripIn)
    {
        Optional<Customer> dbCustomer = customerService.findById(customerId);
        if (dbCustomer.isEmpty()) throw new RuntimeException("Customer with id: " + customerId + " not found");

        Optional<CustomerTrip> dbCustomerTrip = customerTripService.findById(id);
        if (dbCustomerTrip.isEmpty()) throw new RuntimeException("Customer Trip with id: " + id + " not found");

        tripIn.updateCustomerTrip(dbCustomerTrip.get());
        CustomerTrip updatedCustomerTrip = customerTripService.save(dbCustomerTrip.get());
        return new ResponseEntity<>(updatedCustomerTrip, HttpStatus.OK);
    }

    @RequestMapping(value = "/{customerId}/trips/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteCustomerTrip(@PathVariable Long customerId, @PathVariable Long id)
    {
        Optional<Customer> dbCustomer = customerService.findById(customerId);
        if (dbCustomer.isEmpty()) throw new RuntimeException("Customer with id: " + customerId + " not found");

        Optional<CustomerTrip> dbCustomerTrip = customerTripService.findById(id);
        if (dbCustomerTrip.isEmpty()) throw new RuntimeException("Customer trip with id: " + id + " not found");

        customerTripService.delete(dbCustomerTrip.get());
        return new ResponseEntity<>("DELETED", HttpStatus.OK);
    }
}
