package com.pn.groupC.dailyfaces;

public class MessageGroup {
    String username, lastMessage;
    Integer imageUrl;

    public MessageGroup(String username, String lastMessage, Integer imageUrl) {
        this.username = username;
        this.lastMessage = lastMessage;
        this.imageUrl = imageUrl;
    }

    public String getUsername() {
        return username;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public Integer getImageUrl() {
        return imageUrl;
    }
}
