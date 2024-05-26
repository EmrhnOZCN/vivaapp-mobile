package com.example.vivaapp_mobile.model;

public class ChatMessage {
    private String text;
    private int type; // 1: User, 2: Bot

    public ChatMessage(String text, int type) {
        this.text = text;
        this.type = type;
    }

    public ChatMessage(String text) {
        this.text = text;

    }
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
