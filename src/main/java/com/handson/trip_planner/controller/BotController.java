////
//// Source code recreated from a .class file by IntelliJ IDEA
//// (powered by FernFlower decompiler)
////
//
//package com.handson.trip_planner.controller;
//
//import com.handson.trip_planner.model.ChatRequest;
//import com.handson.trip_planner.model.ChatResponse;
//import java.io.IOException;
//
//import com.handson.trip_planner.service.BotService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.client.RestTemplate;
//
//@RestController
//@RequestMapping("/bot")
//@CrossOrigin(origins = "http://localhost:3000")
//public class BotController {
//
//    @Autowired
//    private BotService botService;
//
//    @PostMapping("/chat")
//    public ResponseEntity<String> getTripFromUser(@RequestParam String cityName, @RequestParam Integer tripDays) throws IOException {
//        var prompt = "I’m planning a trip to" + cityName + " from 12/9 until 15/9 and need an itinerary structured as an array of JSON objects. The structure should be as follows:\n" +
//                "Day: id starting with 1. Should also include the date I provided, each day have a different date, and a summary description of what we're doing on each day.\n" +
//                "For each day:\n" +
//                "Activities: A list of activities (such as sightseeing, restaurants, shopping, etc.) with the following details:\n" +
//                "id: A unique identifier for each activity starting from 1.\n" +
//                "color: A unique pastel color (in HEX code) for each activity.\n" +
//                "lat: Latitude of the activity.\n" +
//                "lng: Longitude of the activity.\n" +
//                "address: The full address of the activity.\n" +
//                "place: The name of the place.\n" +
//                "description: A brief description of the activity.\n" +
//                "Please generate a sample itinerary based on the structure above. Include a variety of activities for each day and ensure each activity has a different color. Do not include any other text or explanations or a '```json' text above.";
//        return new ResponseEntity<>(botService.getPromptValue(prompt), HttpStatus.OK);
//    }
//}
