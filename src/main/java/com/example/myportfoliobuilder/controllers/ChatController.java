package com.example.myportfoliobuilder.controllers;

import com.example.myportfoliobuilder.models.*;
import com.example.myportfoliobuilder.services.ChatService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import java.time.Instant;
import java.util.List;
import java.util.Map;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.example.myportfoliobuilder.models.ChatUser;

@Controller
public class ChatController {

    private final ChatService chatService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    public ChatController(ChatService chatService, SimpMessagingTemplate simpMessagingTemplate) {
        this.chatService = chatService;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }
    private void sendMembersList() {
        List<ChatUser> memberList = chatService.getMembersList();
        memberList.forEach(
                sendUser -> simpMessagingTemplate.convertAndSendToUser(sendUser.id(), "/topic/users", chatService.filterMemberListByUser(memberList, sendUser)));
    }

    @MessageMapping("/user")
    public void getusers(ChatUser user, SimpMessageHeaderAccessor headerAccessor) throws Exception {
        ChatUser newUser = new ChatUser(user.id(), null, user.username());
        headerAccessor.getSessionAttributes().put("user", newUser);
        chatService.addMember(newUser);
        sendMembersList();
        Message newMessage = new Message(new ChatUser(null, null, user.username()), null, null, Action.JOINED, Instant.now());
        simpMessagingTemplate.convertAndSend("/topic/messages", newMessage);
    }

    @EventListener
    public void handleSessionConnectEvent(SessionConnectEvent event) {
        System.out.println("Session Connect Event");
    }

    @EventListener
    public void handleSessionDisconnectEvent(SessionDisconnectEvent event) {
        System.out.println("Session Disconnect Event");
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        Map<String, Object> sessionAttributes = headerAccessor.getSessionAttributes();
        if (sessionAttributes == null) {
            return;
        }
        ChatUser user = (ChatUser) sessionAttributes.get("user");
        if (user == null) {
            return;
        }
        chatService.removeMember(user);
        sendMembersList();

        Message message = new Message(new ChatUser(null, null, user.username()), null, "", Action.LEFT, Instant.now());
        simpMessagingTemplate.convertAndSend("/topic/messages", message);
    }

    @MessageMapping("/message")
    public void getMessage(Message message) throws Exception {
        Message newMessage = new Message(new ChatUser(null, message.user().serialId(), message.user().username()), message.receiverId(), message.comment(), message.action(), Instant.now());
        simpMessagingTemplate.convertAndSend("/topic/messages", newMessage);
    }

    @MessageMapping("/privatemessage")
    public void getPrivateMessage(Message message) throws Exception {
        Message newMessage = new Message(new ChatUser(null, message.user().serialId(), message.user().username()), message.receiverId(), message.comment(), message.action(), Instant.now());
        simpMessagingTemplate.convertAndSendToUser(chatService.getMember(message.receiverId()).id(), "/topic/privatemessages", newMessage);

    }

}