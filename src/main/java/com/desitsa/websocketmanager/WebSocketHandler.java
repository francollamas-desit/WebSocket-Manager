package com.desitsa.websocketmanager;

import org.java_websocket.handshake.ServerHandshake;

public class WebSocketHandler {

    private WebSocketManager wsManager;

    public WebSocketManager getWsManager() {
        return wsManager;
    }

    public void setWsManager(WebSocketManager wsManager) {
        this.wsManager = wsManager;
    }

    /**
     * Cuando se abre la conexión con el WebSocket
     */
    public void onOpen(ServerHandshake serverHandshake) { }

    /**
     * Cuando el servidor me manda el ID de mi socket.
     */
    public void onConnected() { }

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
        wsManager.invoke(methodName, args);
    }

    public final void reconnect() {
        wsManager.reconnect();
    }
}
