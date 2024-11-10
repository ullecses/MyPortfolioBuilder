package com.example.myportfoliobuilder.models;

import java.time.Instant;

public record Message(ChatUser user, String comment, Action action, Instant timestamp) {

}