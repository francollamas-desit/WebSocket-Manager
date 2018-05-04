package com.desitsa.websocketmanager;


public class InvocationDescriptor {

    // Nombre del método
    private String methodName;

    // Serie de argumentos (cualquier tipo de objetos)
    private Object[] arguments;

    // Identificador del Método
    private String identifier;



    public String getMethodName() {
        return methodName;
    }


    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }


    public Object[] getArguments() {
        return arguments;
    }


    public void setArguments(Object[] args) {
        this.arguments = args;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
}
