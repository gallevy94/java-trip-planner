package com.handson.trip_planner.controller;
import com.handson.trip_planner.service.MapService;
import com.handson.trip_planner.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/map")
public class MapController {

    @Autowired
    MapService mapService;

    @RequestMapping(value = "/directions", method = RequestMethod.GET)
    public ResponseEntity<String> getDirections(
            @RequestParam double originLat,
            @RequestParam double originLng,
            @RequestParam double destinationLat,
            @RequestParam double destinationLng,
            @RequestParam(required = false) String waypoints) {

        System.out.println("directions");
        String result = mapService.getDirections(originLat, originLng, destinationLat, destinationLng, waypoints);
        System.out.println("result");
        System.out.println(result);

        return ResponseEntity.ok(result);
    }
}