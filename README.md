# WebSocket Manager

Compilar con:

```bash
mvn package
```

Instalar localmente en el sistema:

```bash
mvn org.apache.maven.plugins:maven-install-plugin:2.5:install-file -Dfile="WebSocketManager-1.1.2.jar"
```

Agregar dependencias a un proyecto

```xml
<!-- Websocket Manager -->
<dependency>
    <groupId>com.desitsa</groupId>
    <artifactId>websocketmanager</artifactId>
    <version>1.1.2</version>
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