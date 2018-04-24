package com.desitsa.websocketmanager;

import com.google.gson.*;
import javafx.application.Platform;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Maneja la conexión con el servidor
 */
public class WebSocketManager {

    private String url;
    private WebSocketClient ws;

    // ID de websoket asignada por el servidor
    private String connectionID;

    // Objeto que contiene los mensajes
    private WebSocketHandler handler;


    public WebSocketManager(String url, Class<? extends WebSocketHandler> handler) {
        this.url = url;

        // Crea el handler de mensajes
        try {
            this.handler = handler.newInstance();
            this.handler.setWsManager(this);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }


    /**
     * Se conecta con el servidor
     */
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

        // Creamos la conexión con el servidor
        ws = new WebSocketClient(uri) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                Platform.runLater(() -> WebSocketManager.this.handler.onOpen(serverHandshake));
            }

            @Override
            public void onMessage(String s) {

                // Objeto que crea JSONs
                JsonParser parser = new JsonParser();

                // Desglosa el mensaje en un objeto JSON.
                JsonObject jo = parser.parse(s).getAsJsonObject();

                // Creación del mensaje
                Message msg = new Message();
                msg.setMessageType(MessageType.values()[jo.get("messageType").getAsInt()]);
                msg.setData(jo.get("data").getAsString());

                // Le decimos al hilo de JavaFX que ejecute las acciones.
                Platform.runLater(() -> {
                    // Actúa según el tipo de mensaje
                    switch (msg.getMessageType()) {
                        case Text:
                            WebSocketManager.this.handler.onTextMessage(msg.getData());
                            break;

                        case ClientMethodInvocation:
                            JsonObject o = parser.parse(msg.getData()).getAsJsonObject();
                            String method = o.get("methodName").getAsString();
                            JsonArray arguments = o.get("arguments").getAsJsonArray();

                            // Crea un array con los argumentos del método
                            String[] array = new String[arguments.size()];
                            for (int i = 0; i < arguments.size(); i++) {
                                array[i] = arguments.get(i).getAsString();
                            }

                            // Intenta invocar al método si es que existe.
                            try {
                                Method m = WebSocketManager.this.handler.getClass().getDeclaredMethod(method, String[].class);
                                m.invoke(handler, new Object[]{array});

                            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                                e.printStackTrace();
                            }
                            break;

                        case ConnectionEvent:
                            WebSocketManager.this.connectionID = msg.getData();
                            WebSocketManager.this.handler.onConnected();
                            break;
                    }
                });

            }

            @Override
            public void onClose(int i, String s, boolean b) {
                Platform.runLater(() -> WebSocketManager.this.handler.onClose(i, s, b));
            }

            @Override
            public void onError(Exception e) {
                Platform.runLater(() -> WebSocketManager.this.handler.onError(e));
            }
        };

        // Conectamos con el servidor
        ws.connect();
    }


    /**
     * Cierra la conexión actual si es que existe y no está cerrada
     */
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


    public WebSocketHandler getHandler() {
        return handler;
    }


    public String getConnectionID() {
        return connectionID;
    }

}
