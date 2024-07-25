//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.handson.trip_planner.controller;

import com.handson.trip_planner.model.ChatRequest;
import com.handson.trip_planner.model.ChatResponse;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/bot")
public class BotController {
    @Value("${openai.model}")
    private String model;
    @Value("${openai.api.url}")
    private String apiURL;
    @Autowired
    private RestTemplate template;

    @RequestMapping(value = {"/chat"}, method = {RequestMethod.GET})
    public ResponseEntity<?> getPrompt(@RequestParam String prompt) throws IOException {
        ChatRequest request = new ChatRequest(model, prompt);
        ChatResponse response = template.postForObject(apiURL, request, ChatResponse.class);
        return new ResponseEntity<>(response.getChoices().get(0).getMessage().getContent(), HttpStatus.OK);
    }
}
