package com.example.myportfoliobuilder.models;

import java.time.Instant;

public record Message(ChatUser user,  String receiverId, String comment, Action action, Instant timestamp) {

}