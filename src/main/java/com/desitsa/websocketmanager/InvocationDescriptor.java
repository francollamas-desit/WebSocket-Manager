package com.desitsa.websocketmanager;

public class InvocationDescriptor {
    private String methodName;

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
