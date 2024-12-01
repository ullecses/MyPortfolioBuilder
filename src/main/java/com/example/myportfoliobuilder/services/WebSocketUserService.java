package com.example.myportfoliobuilder.services;

import org.springframework.web.socket.WebSocketSession;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.Set;

@Service
public class WebSocketUserService {

    private Set<WebSocketSession> sessions = new HashSet<>();

    public void addSession(WebSocketSession session) {
        sessions.add(session);
    }

    public void removeSession(WebSocketSession session) {
        sessions.remove(session);
    }

    public Set<WebSocketSession> getSessions() {
        return sessions;
    }
}
