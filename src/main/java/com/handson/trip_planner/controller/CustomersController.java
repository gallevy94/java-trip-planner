//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.handson.trip_planner.controller;

import com.handson.trip_planner.model.Customer;
import com.handson.trip_planner.model.CustomerIn;
import com.handson.trip_planner.service.CustomerService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/api/customer"})
public class CustomersController {
    @Autowired
    CustomerService customerService;

    public CustomersController() {
    }

    @RequestMapping(
            value = {""},
            method = {RequestMethod.GET}
    )
    public ResponseEntity<?> getAllCustomers() {
        return new ResponseEntity(this.customerService.all(), HttpStatus.OK);
    }

    @RequestMapping(
            value = {"/{id}"},
            method = {RequestMethod.GET}
    )
    public ResponseEntity<?> getOneCustomer(@PathVariable Long id) {
        return new ResponseEntity(this.customerService.findById(id), HttpStatus.OK);
    }

    @RequestMapping(
            value = {""},
            method = {RequestMethod.POST}
    )
    public ResponseEntity<?> insertCustomer(@RequestBody CustomerIn customerIn) {
        Customer customer = customerIn.toCustomer();
        customer = this.customerService.save(customer);
        return new ResponseEntity(customer, HttpStatus.OK);
    }

    @RequestMapping(
            value = {"/{id}"},
            method = {RequestMethod.PUT}
    )
    public ResponseEntity<?> updateCustomer(@PathVariable Long id, @RequestBody CustomerIn student) {
        Optional<Customer> dbCustomer = this.customerService.findById(id);
        if (dbCustomer.isEmpty()) {
            throw new RuntimeException("Customer with id: " + id + " not found");
        } else {
            student.updateCustomer((Customer)dbCustomer.get());
            Customer updatedCustomer = this.customerService.save((Customer)dbCustomer.get());
            return new ResponseEntity(updatedCustomer, HttpStatus.OK);
        }
    }

    @RequestMapping(
            value = {"/{id}"},
            method = {RequestMethod.DELETE}
    )
    public ResponseEntity<?> deleteCustomer(@PathVariable Long id) {
        Optional<Customer> dbCustomer = this.customerService.findById(id);
        if (dbCustomer.isEmpty()) {
            throw new RuntimeException("Customer with id: " + id + " not found");
        } else {
            this.customerService.delete((Customer)dbCustomer.get());
            return new ResponseEntity("DELETED", HttpStatus.OK);
        }
    }
}
