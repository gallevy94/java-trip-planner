package com.handson.trip_planner.controller;

import com.handson.trip_planner.model.Customer;
import com.handson.trip_planner.model.CustomerTrip;
import com.handson.trip_planner.model.TripIn;
import com.handson.trip_planner.service.BotService;
import com.handson.trip_planner.service.CustomerService;
import com.handson.trip_planner.service.CustomerTripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/customers")
public class CustomersTripsController {
    @Autowired
    CustomerService customerService;

    @Autowired
    CustomerTripService customerTripService;

    @Autowired
    private BotService botService;

    @RequestMapping(value = "/trip-plan", method = RequestMethod.POST)
    public ResponseEntity<String> getTripPlan(@RequestParam String cityName, @RequestParam Integer tripDays, @RequestParam Long customerId, HttpSession session) throws IOException {

        String prompt = "Plan me a simple " + tripDays + " days vacation in " + cityName + " with all kinds of activities (site seeing, restaurant, shopping, etc). Return only a JSON array of objects, each with fields for 'id' (starting with 1), 'color' (different color for each activity, pastel color and should be HEX code), 'lat' and 'lng', 'address', 'place' and 'description'. Do not include any other text or explanations or a '```json' text above.";
        String tripPlan = botService.getPromptValue(prompt);

        // Create and set the trip details in the session
        TripIn tripIn = TripIn.TripInBuilder.aTripIn()
                .cityName(cityName)
                .tripDays(tripDays)
                .tripPlan(tripPlan)
                .build();
        session.setAttribute("tripIn_" + customerId, tripIn);

        return new ResponseEntity<>(tripPlan, HttpStatus.OK);
    }

    @RequestMapping(value = "/{customerId}/trips", method = RequestMethod.POST)
    public ResponseEntity<?> insertCustomerTrip(@PathVariable Long customerId, HttpSession session) {

        // Retrieve the TripIn object from session
        TripIn tripIn = (TripIn) session.getAttribute("tripIn_" + customerId);
        if (tripIn == null) {
            return new ResponseEntity<>("Trip data not found in session.", HttpStatus.BAD_REQUEST);
        }

        var customer = customerService.findById(customerId);
        if (customer.isEmpty()) {
            throw new RuntimeException("Customer:" + customerId + " not found");
        }

        CustomerTrip customerTrip = tripIn.toTrip(customer.get());
        customerTrip = customerTripService.save(customerTrip);

        session.removeAttribute("tripIn_" + customerId);

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
