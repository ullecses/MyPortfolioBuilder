package com.example.myportfoliobuilder.controllers;

import com.example.myportfoliobuilder.models.Greeting;
import com.example.myportfoliobuilder.models.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class ChatController {
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(Message message) throws Exception {
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.name()) + "!");
    }
}
