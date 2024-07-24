package com.handson.trip_planner.model;

import java.util.List;

public class ChatResponse {
    private List<Choice> choices;

    public ChatResponse() {
    }

    public List<Choice> getChoices() {
        return this.choices;
    }

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }

    public class Choice {
        private int index;
        private ChatMessage message;

        public Choice() {
        }

        public int getIndex() {
            return this.index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public ChatMessage getMessage() {
            return this.message;
        }

        public void setMessage(ChatMessage message) {
            this.message = message;
        }
    }
}
