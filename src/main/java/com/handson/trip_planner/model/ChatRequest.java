package com.handson.trip_planner.model;

import java.util.ArrayList;
import java.util.List;

public class ChatRequest {
    private String model;
    private List<ChatMessage> messages;

    public ChatRequest(String model, String prompt) {
        this.model = model;
        this.messages = new ArrayList();
        this.messages.add(new ChatMessage("user", prompt));
    }

    public String getModel() {
        return this.model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<ChatMessage> getMessages() {
        return this.messages;
    }

    public void setMessages(List<ChatMessage> messages) {
        this.messages = messages;
    }

    public String toString() {
        return "ChatRequest{model='" + this.model + "', messages=" + this.messages + "}";
    }
}
