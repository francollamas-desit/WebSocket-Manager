package com.desitsa.websocketmanager;

public class InvocationResult {

    private String $type;

    // Identificador del método
    private JnValue identifier;

    // Resultado del método
    private Object result; // TODO: dejarlo en Object???

    // Excepción por algun motivo
    private JnException exception;

    public InvocationResult() {
        this.$type = "WebSocketManager.Common.InvocationResult";
    }

    public String get$type() {
        return $type;
    }

    public String getIdentifier() {
        return (String)identifier.$value;
    }

    public void setIdentifier(String identifier) {
        this.identifier = new JnValue("System.Guid", identifier);
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public String getException() {
        return (String)exception.message;
    }

    public void setException(String message) {
        this.exception = new JnException("WebSocketManager.Common.RemoteException", message);
    }
}
