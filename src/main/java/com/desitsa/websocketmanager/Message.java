package com.desitsa.websocketmanager;

/**
 * Hace referencia a un mensaje que el cliente puede interpretar
 */
public class Message {

    // Tipo de mensaje
    private MessageType messageType;

    // Información del mensaje (en JSON)
    private String data;


    public Message() { }


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
