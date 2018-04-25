# WebSocket-Manager

Compilar con:

```mvn package```

Instalar localmente en el sistema:

```mvn org.apache.maven.plugins:maven-install-plugin:2.5:install-file -Dfile="WebSocketManager-1.0.jar"```

Agregar dependencias a un proyecto

```
<!-- Websocket Manager -->
<dependency>
    <groupId>com.desitsa</groupId>
    <artifactId>websocketmanager</artifactId>
    <version>1.0</version>
</dependency>

<!-- WebSockets -->
<dependency>
    <groupId>org.java-websocket</groupId>
    <artifactId>Java-WebSocket</artifactId>
    <version>1.3.8</version>
</dependency>

<!-- Manejador de JSON -->
<dependency>
    <groupId>com.google.code.gson</groupId>
    <artifactId>gson</artifactId>
    <version>2.8.2</version>
</dependency>
```