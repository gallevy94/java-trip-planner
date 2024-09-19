package com.handson.trip_planner.controller;

import com.google.maps.model.LatLng;
import com.handson.trip_planner.model.*;
import com.handson.trip_planner.service.BotService;
import com.handson.trip_planner.service.CustomerService;
import com.handson.trip_planner.service.MapService;
import com.handson.trip_planner.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/trip")
public class TripsController {
    @Autowired
    CustomerService customerService;

    @Autowired
    BotService botService;

    @Autowired
    TripService tripService;

    @Autowired
    MapService mapService;

    @RequestMapping(value = "/trip-plan", method = RequestMethod.POST)
    public ResponseEntity<TripResponse> getTripPlan(@RequestParam String cityName, @RequestParam String startDate, @RequestParam String endDate, @RequestParam Long customerId, HttpSession session) throws IOException {

        String prompt = "I’m planning a trip to" + cityName + " from" + startDate + " until" + endDate + " and need an itinerary structured as an array of JSON objects. The structure should be as follows: id: A unique identifier for each activity starting from 0. Should also include the date I provided, each day have a different date in DD/MM format and a summary on what we are doing on each day. For each day: activities: A list of activities (such as sightseeing, restaurants, shopping, etc.) with the following details: id: A unique identifier for each activity starting from 0, continue the count for each activity, don't reset the count back to 0. time: 'startTime-EndTime' , time in a day to visit the activity. lat: Latitude of the activity. lng: Longitude of the activity. address: The full address of the activity. place: The name of the place. description: A brief description of the activity. Please generate an itinerary based on the structure above. Include a variety of activities for each day. Do not include any other text or explanations or a '```json' text above.";
        String tripPlan = botService.getPromptValue(prompt);
        List<List<LatLng>> coordinates = mapService.extractCoordinatesFromPlan(tripPlan);
        List<String> imagesUrls = mapService.getImageUrlsForPlaces(tripPlan, cityName);

        TripResponse response = new TripResponse(tripPlan, coordinates, imagesUrls);

        // Create and set the trip details in the session
        TripIn tripIn = TripIn.TripInBuilder.aTripIn()
                .cityName(cityName)
                .startDate(startDate)
                .endDate(endDate)
                .tripPlan(tripPlan)
                .coordinates(coordinates)
                .imagesUrls(imagesUrls)
                .build();
        session.setAttribute("tripIn_" + customerId, tripIn);

        return new ResponseEntity<>(response, HttpStatus.OK);
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

        Trip trip = tripIn.toTrip(customer.get());
        trip = tripService.save(trip);

        session.removeAttribute("tripIn_" + customerId);

        return new ResponseEntity<>(trip, HttpStatus.OK);
    }


    @RequestMapping(value = "", method = {RequestMethod.GET})
    public ResponseEntity<?> getAllTrips() {
        return new ResponseEntity<>(tripService.all(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = {RequestMethod.GET})
    public ResponseEntity<?> getOneTrip(@PathVariable Long id) {
        return new ResponseEntity<>(tripService.findById(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/{customerId}/trips/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteTrip(@PathVariable Long customerId, @PathVariable Long id)
    {
        Optional<Customer> dbCustomer = customerService.findById(customerId);
        if (dbCustomer.isEmpty()) throw new RuntimeException("Customer with id: " + customerId + " not found");

        Optional<Trip> dbCustomerTrip = tripService.findById(id);
        if (dbCustomerTrip.isEmpty()) throw new RuntimeException("Customer trip with id: " + id + " not found");

        tripService.delete(dbCustomerTrip.get());
        return new ResponseEntity<>("DELETED", HttpStatus.OK);
    }
}
