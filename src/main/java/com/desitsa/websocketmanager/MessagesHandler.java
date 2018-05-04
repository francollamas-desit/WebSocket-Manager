package com.desitsa.websocketmanager;

import org.java_websocket.handshake.ServerHandshake;

/**
 * Se manejan todos los mensajes y eventos que llegan, de cualquier tipo
 */
public abstract class MessagesHandler {

    // Referencia al Connection
    private Connection connection;


    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    /**
     * Cuando se abre la conexión con el WebSocket
     */
    public void onOpen(ServerHandshake serverHandshake) { }

    /**
     * Cuando el servidor me manda el ID de mi socket.
     */
    public void onConnected(String connectionID) { }

    /**
     * Recibe un texto simple
     */
    public void onTextMessage(String message) { }

    /**
     * Cuando se cierra la conexión
     */
    public void onClose(int i, String s, boolean b) { }

    /**
     * Cuando hay un error en la conexión
     */
    public void onError(Exception e) { }


    public final void invoke(String methodName, Object... args) {
        connection.invoke(methodName, args);
    }

    public final void reconnect() {
        connection.reconnect();
    }
}
