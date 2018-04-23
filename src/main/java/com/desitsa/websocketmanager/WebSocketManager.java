package com.desitsa.websocketmanager;

import com.google.gson.*;
import javafx.application.Platform;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class WebSocketManager {

    private String url;
    private WebSocketClient ws;

    // ID de websoket asignada por el servidor
    private String connectionID;

    // Objeto que contiene los mensajes
    private WebSocketHandler messages;

    private ArrayList<String> in;


    public WebSocketManager(String url, Class<? extends WebSocketHandler> messages) {
        this.url = url;

        // Crea el handler de mensajes
        try {
            this.messages = messages.newInstance();
            this.messages.setWsManager(this);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    public void start() {
        URI uri = null;
        try {
            uri = new URI(url);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        if (uri == null) return;

        // Si había una conexión, la cerramos
        close();

        ws = new WebSocketClient(uri) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                Platform.runLater(() -> WebSocketManager.this.messages.onOpen(serverHandshake));
            }

            @Override
            public void onMessage(String s) {

                // Objetos para manejar JSON.
                JsonParser parser = new JsonParser();
                JsonObject jo;

                // Obtiene el tipo de mensaje y el mensaje
                jo = parser.parse(s).getAsJsonObject();
                Message msg = new Message();
                msg.setMessageType(MessageType.values()[jo.get("messageType").getAsInt()]);
                msg.setData(jo.get("data").getAsString());

                // Le decimos al hilo de JavaFX que ejecute las acciones.
                Platform.runLater(() -> {
                    // Actúa según el tipo de mensaje
                    switch (msg.getMessageType()) {
                        case Text:
                            WebSocketManager.this.messages.onTextMessage(msg.getData());
                            break;

                        case ClientMethodInvocation:
                            JsonObject o = parser.parse(msg.getData()).getAsJsonObject();
                            String method = o.get("methodName").getAsString();
                            JsonArray arguments = o.get("arguments").getAsJsonArray();

                            String[] array = new String[arguments.size()];
                            for (int i = 0; i < arguments.size(); i++) {
                                array[i] = arguments.get(i).getAsString();
                            }
                            try {
                                Method m = WebSocketManager.this.messages.getClass().getDeclaredMethod(method, String[].class);
                                m.invoke(messages, new Object[]{array});

                            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                                e.printStackTrace();
                            }
                            break;

                        case ConnectionEvent:
                            WebSocketManager.this.connectionID = msg.getData();
                            WebSocketManager.this.messages.onConnected();
                            break;
                    }
                });

            }

            @Override
            public void onClose(int i, String s, boolean b) {
                Platform.runLater(() -> WebSocketManager.this.messages.onClose(i, s, b));
            }

            @Override
            public void onError(Exception e) {
                Platform.runLater(() -> WebSocketManager.this.messages.onError(e));
            }
        };


        ws.connect();
    }

    public void close() {
        if (ws != null && !ws.isClosed())
           ws.close();
    }

    /**
     * Cierra la conexión actual si es que existe y vuelve a conectarla.
     */
    public void reconnect() {
        if (ws != null)
            ws.reconnect();
    }

    /**
     * Invoca un método del servidor
     *
     * @param methodName nombre del método a llamar
     * @param args objetos que luego son serializados a JSON.
     */
    public void invoke(String methodName, Object... args) {

        InvocationDescriptor invDesc = new InvocationDescriptor();
        invDesc.setMethodName(methodName);
        invDesc.setArgs(args);

        Gson json = new Gson();
        JsonElement e = json.toJsonTree(invDesc);

        if (ws.isOpen())
            ws.send(e.toString());
    }

    public WebSocketHandler getMessages() {
        return messages;
    }

    public String getConnectionID() {
        return connectionID;
    }

}
