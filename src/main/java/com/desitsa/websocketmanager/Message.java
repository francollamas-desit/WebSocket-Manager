package com.desitsa.websocketmanager;

public class Message {

    private String $type;

    // Tipo de mensaje
    private MessageType messageType;

    // Información del mensaje (en JSON)
    private String data;


    public Message() {
        this.$type = "WebSocketManager.Common.Message";
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
