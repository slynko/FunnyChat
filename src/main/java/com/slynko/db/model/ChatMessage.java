package com.slynko.db.model;

import java.util.Date;

public class ChatMessage {
    private String message;
    private String sender;
    private Date received;
    private boolean hasConnected;
    private boolean hasDisconnected;
    private boolean isTyping;

    public ChatMessage() {
        message = "";
        received = new Date();
        sender = "";
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public Date getReceived() {
        return received;
    }

    public void setReceived(Date received) {
        this.received = received;
    }

    public boolean hasConnected() {
        return hasConnected;
    }

    public void setConnected(boolean hasConnected) {
        this.hasConnected = hasConnected;
    }

    public boolean hasDisconnected() {
        return hasDisconnected;
    }

    public void setDisconnected(boolean hasDisconnected) {
        this.hasDisconnected = hasDisconnected;
    }

    public boolean isTyping() {
        return isTyping;
    }

    public void setTyping(boolean typing) {
        isTyping = typing;
    }
}
