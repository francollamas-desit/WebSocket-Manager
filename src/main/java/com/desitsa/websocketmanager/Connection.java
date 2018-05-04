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
public class Connection {

    private static final String EMPTY_GUID = "00000000-0000-0000-0000-000000000000";

    // Referencia al servidor
    private URI uri;

    // WebSocket
    private WebSocketClient websocket;

    // ID de websoket asignada por el servidor
    private String connectionID;

    // Objeto que contiene los mensajes
    private MessagesHandler messages;


    public Connection(String url, Class<? extends MessagesHandler> messages) {

        // Setea la URL
        try {
            uri = new URI(url);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        // Crea el messages de mensajes
        try {
            this.messages = messages.newInstance();
            this.messages.setConnection(this);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }


    /**
     * Se conecta con el servidor
     */
    public void start() {

        // Si la URL es inválida, no se puede conectar
        if (uri == null) return;

        // Si había una conexión, la cerramos
        close();

        // Creamos la conexión con el servidor
        websocket = new WebSocketClient(uri) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                Platform.runLater(() -> Connection.this.messages.onOpen(serverHandshake));
            }

            @Override
            public void onMessage(String s) {


                // Le decimos al hilo de JavaFX que ejecute las acciones.
                Platform.runLater(() -> {

                    System.out.println(s);
                    // TODO: por el momento se ignora todo tipo de dato que llega

                    // Objeto que permite desglosar un JSON.
                    JsonParser parser = new JsonParser();

                    // Obtiene el JSON principal.
                    JsonObject json = parser.parse(s).getAsJsonObject();

                    // Creación del mensaje
                    Message msg = new Message();
                    msg.setMessageType(MessageType.values()[json.get("messageType").getAsInt()]);
                    msg.setData(json.get("data").getAsJsonObject().get("$value").getAsString());

                    switch (msg.getMessageType()) {
                        case ConnectionEvent:
                            Connection.this.connectionID = msg.getData();
                            Connection.this.messages.onConnected(Connection.this.connectionID);
                            break;

                        case Text:
                            Connection.this.messages.onTextMessage(msg.getData());
                            break;

                        case MethodInvocation:
                            json = parser.parse(msg.getData()).getAsJsonObject();

                            // Crea la descripción del método
                            InvocationDescriptor invDesc = new InvocationDescriptor();
                            invDesc.setMethodName(json.get("methodName").getAsJsonObject().get("$value").getAsString());
                            invDesc.setIdentifier(json.get("identifier").getAsJsonObject().get("$value").getAsString());

                            JsonArray arguments = json.get("arguments").getAsJsonObject().get("$values").getAsJsonArray();
                            String[] array = new String[arguments.size()];
                            for (int i = 0; i < arguments.size(); i++) {
                                array[i] = arguments.get(i).getAsJsonObject().get("$value").getAsString();
                            }
                            invDesc.setArguments(array);


                            // Ejecuta el Método
                            try {
                                Method m = Connection.this.messages.getClass().getDeclaredMethod(invDesc.getMethodName(), String[].class);
                                Object result = m.invoke(messages, new Object[]{array});

                                // Si la invocación desde servidor espera una respuesta, y mi método dio una respuesta...
                                if (!invDesc.getIdentifier().equals(EMPTY_GUID)) {
                                    if (result != null) {
                                        // TODO: implementar (enviar respuesta)
                                    }
                                    else {
                                        // TODO: enviar excepción, diciendo que el método no retorno una respuesta
                                    }
                                }

                            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                                // Si hubo un problema al llamar al método...
                                e.printStackTrace();

                                // Le avisamos al servidor en el caso de que haya esperado una respuesta
                                if (!invDesc.getIdentifier().equals(EMPTY_GUID)) {
                                    // TODO: mandar excepción, diciendo que no se puede devolver un resultado.
                                }
                            }

                            break;

                        case MethodReturnValue:
                            // TODO: implementar
                            break;
                    }
                });
            }

            @Override
            public void onClose(int i, String s, boolean b) {
                Platform.runLater(() -> Connection.this.messages.onClose(i, s, b));
            }

            @Override
            public void onError(Exception e) {
                Platform.runLater(() -> Connection.this.messages.onError(e));
            }
        };

        // Conectamos con el servidor
        websocket.connect();
    }


    /**
     * Cierra la conexión actual si es que existe y no está cerrada
     */
    public void close() {
        if (websocket != null && !websocket.isClosed())
           websocket.close();
    }


    /**
     * Cierra la conexión actual si es que existe y vuelve a conectarla.
     */
    public void reconnect() {
        if (websocket != null)
            websocket.reconnect();
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
        invDesc.setArguments(args);

        Gson json = new Gson();
        JsonElement e = json.toJsonTree(invDesc);

        if (websocket.isOpen())
            //websocket.send(e.toString());
            System.out.println(e.toString());
    }

    /**
     * Invoca un método del servidor sin esperar una respuesta
     * @param methodName
     * @param args
     */
    public void invokeOnly(String methodName, Object... args) {

        Gson json = new Gson();

        InvocationDescriptor invDesc = new InvocationDescriptor();
        invDesc.setMethodName(methodName);
        invDesc.setArguments(args);
        invDesc.setIdentifier(EMPTY_GUID);

        Message msg = new Message();
        msg.setMessageType(MessageType.MethodInvocation);
        msg.setData(json.toJsonTree(invDesc).toString());

        System.out.println(json.toJsonTree(msg).toString());

    }


    public MessagesHandler getMessages() {
        return messages;
    }


    public String getConnectionID() {
        return connectionID;
    }

}
