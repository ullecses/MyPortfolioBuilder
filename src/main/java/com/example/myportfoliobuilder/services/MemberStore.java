package com.example.myportfoliobuilder.services;

import java.util.LinkedList;
import java.util.List;

import com.example.myportfoliobuilder.models.ChatUser;
import org.springframework.stereotype.Service;



@Service
public class MemberStore {

    private static List<ChatUser> store = new LinkedList<>();

    public List<ChatUser> getMembers() {
        return store;
    }

    public void addMember(ChatUser member) {
        store.add(member);
    }

    public void removeMember(ChatUser member) {
        store.remove(member);
    }
}