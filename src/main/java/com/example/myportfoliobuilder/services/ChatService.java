package com.example.myportfoliobuilder.services;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.example.myportfoliobuilder.models.ChatUser;
import org.springframework.stereotype.Service;


@Service
public class ChatService {

    private static List<ChatUser> store = new LinkedList<>();


    public List<ChatUser> getMembersList() {
        AtomicInteger serialId = new AtomicInteger(1);
        return store.stream()
                .map(user -> new ChatUser(user.id(), serialId.getAndIncrement() + "", user.username()))
                .toList();
    }

    public List<ChatUser> filterMemberListByUser(List<ChatUser> memberList, ChatUser user) {
        return memberList.stream()
                .filter(filterUser -> filterUser.id() != user.id())
                .map(sendUser -> new ChatUser(null, sendUser.serialId(), sendUser.username()))
                .toList();
    }

    public ChatUser getMember(String id) {
        return store.get(Integer.valueOf(id) - 1);
    }

    public void addMember(ChatUser member) {
        store.add(member);
    }

    public void removeMember(ChatUser member) {
        store.remove(member);
    }
}