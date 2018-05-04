package com.desitsa.websocketmanager;

public class Message {

    private String $type;

    // Tipo de mensaje
    private int messageType;

    // Información del mensaje (en JSON)
    private JnValue data;


    public Message() {
        this.$type = "WebSocketManager.Common.Message";
    }

    public MessageType getMessageType() {
        return MessageType.values()[messageType];
    }


    public void setMessageType(MessageType messageType) {
        this.messageType = messageType.ordinal();
    }


    public String getData() {
        return (String)data.$value;
    }


    public void setData(String data) {
        this.data = new JnValue("System.String", data);
    }
}
