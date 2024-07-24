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
@RequestMapping({"/bot"})
public class BotController {
    @Value("${openai.model}")
    private String model;
    @Value("${openai.api.url}")
    private String apiURL;
    @Autowired
    private RestTemplate template;

    public BotController() {
    }

    @RequestMapping(
            value = {"/gpt"},
            method = {RequestMethod.GET}
    )
    public ResponseEntity<?> getPrompt(@RequestParam String prompt) throws IOException {
        ChatRequest request = new ChatRequest(this.model, prompt);
        ChatResponse response = (ChatResponse)this.template.postForObject(this.apiURL, request, ChatResponse.class, new Object[0]);
        return new ResponseEntity(((ChatResponse.Choice)response.getChoices().get(0)).getMessage().getContent(), HttpStatus.OK);
    }
}
