package com.handson.trip_planner.service;

import com.handson.trip_planner.model.ChatRequest;
import com.handson.trip_planner.model.ChatResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;



@Service
public class BotService {
    @Value("${openai.model}")
    private String model;
    @Value("${openai.api.url}")
    private String apiURL;
    @Autowired
    private RestTemplate template;

    public String getPromptValue(String prompt) throws IOException {
        ChatRequest request = new ChatRequest(model, prompt);
        ChatResponse response = template.postForObject(apiURL, request, ChatResponse.class);
        return response.getChoices().get(0).getMessage().getContent();
    }
}
