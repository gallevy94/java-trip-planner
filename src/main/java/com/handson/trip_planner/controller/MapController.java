//package com.handson.trip_planner.controller;
//
//import com.handson.trip_planner.model.TripIn;
//import com.handson.trip_planner.service.MapService;
//import org.hibernate.mapping.Map;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.json.JSONObject;
//
//import java.io.IOException;
//
//@RestController
//@CrossOrigin(origins = "http://localhost:3000")
//public class MapController {
//
//    private final MapService mapService;
//
//    public MapController(MapService geocodingService) {
//        this.mapService = geocodingService;
//    }
//
//
//    @RequestMapping(value = "/getCoordinates", method = RequestMethod.POST)
//    public ResponseEntity<String> getCoordinates(@RequestParam String trip) throws IOException {
//        String locations = mapService.getRoutesForTripPlan(trip);
//        return new ResponseEntity<>(locations, HttpStatus.OK);
//    }
//}