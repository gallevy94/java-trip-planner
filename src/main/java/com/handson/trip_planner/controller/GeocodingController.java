package com.handson.trip_planner.controller;

import com.handson.trip_planner.service.GeocodingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.json.JSONObject;

import java.io.IOException;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class GeocodingController {

    private final GeocodingService geocodingService;

    public GeocodingController(GeocodingService geocodingService) {
        this.geocodingService = geocodingService;
    }

    @GetMapping("/getLatLng")
    public ResponseEntity<String> getLatLng(@RequestParam String cityName) throws IOException {
        JSONObject location = geocodingService.getLatLngFromCity(cityName);
        return new ResponseEntity<>(location.toString(), HttpStatus.OK);
    }
//    @GetMapping("/getLatLng")
//    public String getLatLng(@RequestParam String city) {
//        JSONObject location = geocodingService.getLatLngFromCity(city);
//        if (location != null) {
//            return location.toString();
//        }
//        return "Location not found";
//    }
}
