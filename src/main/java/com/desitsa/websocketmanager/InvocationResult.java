package com.desitsa.websocketmanager;

public class InvocationResult {

    private String $type;

    private JnValue result;
    // Resultado del método

    // Excepción por algun motivo
    private JnException exception;

    // Identificador del método
    private JnValue identifier;

    public InvocationResult() {
        this.$type = "WebSocketManager.Common.InvocationResult";
    }

    public String getIdentifier() {
        return (String)identifier.$value;
    }

    public void setIdentifier(String identifier) {
        this.identifier = new JnValue("System.Guid", identifier);
    }

    public Object getResult() {
        return Util.convertToJavaType(result.$type, result.$value);
    }

    public void setResult(Object result) {
        this.result = new JnValue(Util.getCSharpType(result.getClass()), result);
    }

    public String getException() {
        return (String)exception.message;
    }

    public void setException(String message) {
        this.exception = new JnException("WebSocketManager.Common.RemoteException", message);
    }
}
