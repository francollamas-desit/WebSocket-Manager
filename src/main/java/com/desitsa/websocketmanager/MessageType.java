package com.desitsa.websocketmanager;


public enum MessageType {

    // Es simplemente un mensaje de texto para usarlo a conveniencia.
    Text,

    // Invocación a un método
    MethodInvocation,

    // Evento de conexión (cuando el servidor reconoce al cliente y le manda su ID de WebSocket).
    ConnectionEvent,

    // Retorno de un método
    MethodReturnValue
}
