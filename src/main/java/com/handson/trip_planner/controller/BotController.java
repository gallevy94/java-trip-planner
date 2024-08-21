//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.handson.trip_planner.controller;

import com.handson.trip_planner.model.ChatRequest;
import com.handson.trip_planner.model.ChatResponse;
import java.io.IOException;

import com.handson.trip_planner.service.BotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/bot")
@CrossOrigin(origins = "http://localhost:3000")
public class BotController {

    @Autowired
    private BotService botService;

    @PostMapping("/chat")
    public ResponseEntity<String> getTripFromUser(@RequestParam String cityName, @RequestParam Integer tripDays) throws IOException {
        var prompt = "Plan me a simple " + tripDays + "days vacation in " + cityName + "with all kinds of activities (site seeing, restaurant, shopping .etc) . Return only a JSON array of objects, each with fields for 'id' (starting with 1), 'color' (different color for each activity,pastel color and should be HEX code) 'lat' and 'lng', 'address', 'place' and 'description'. Do not include any other text or explanations or a '```json' text above.";
        return new ResponseEntity<>(botService.getPromptValue(prompt), HttpStatus.OK);
    }
}
