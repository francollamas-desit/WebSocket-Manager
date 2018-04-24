package com.desitsa.websocketmanager;

/**
 * Hace referencia a un mensaje que el servidor puede interpretar
 */
public class InvocationDescriptor {

    // Nombre del método
    private String methodName;

    // Serie de argumentos (cualquier tipo de objetos)
    private Object[] arguments;


    public String getMethodName() {
        return methodName;
    }


    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }


    public Object[] getArgs() {
        return arguments;
    }


    public void setArgs(Object[] args) {
        this.arguments = args;
    }
}
