package com.desitsa.websocketmanager;

public class Message {

    private MessageType messageType;
    private String data;

    public Message() {

    }

    public Message(MessageType messageType, String data) {
        setMessageType(messageType);
        this.data = data;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
