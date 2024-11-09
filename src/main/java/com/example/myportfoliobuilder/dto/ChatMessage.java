package com.example.myportfoliobuilder.dto;

import java.time.LocalDateTime;

public class ChatMessage {

    private String name;

    public ChatMessage(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
