package com.desitsa.websocketmanager;


public class InvocationDescriptor {

    private String $type;

    // Nombre del método
    private JnValue methodName;

    // Serie de argumentos (cualquier tipo de objetos)
    private JnValues arguments;

    // Identificador del Método
    private JnValue identifier;

    public InvocationDescriptor() {
        this.$type = "WebSocketManager.Common.InvocationDescriptor";
    }

    public String getMethodName() {
        return (String)methodName.$value;
    }


    public void setMethodName(String methodName) {
        this.methodName = new JnValue("System.String", methodName);
    }


    public String[] getArguments() { // TODO: este metodo tiene que castear y devolver en STRING???
        String[] array = new String[arguments.$values.length];
        for (int i = 0; i < arguments.$values.length; i++) {
            array[i] = (String)arguments.$values[i].$value;
        }
        return array;
    }


    public void setArguments(Object[] args) {
        JnValue[] array = new JnValue[args.length];
        for (int i = 0; i < args.length; i++) {
            array[i] = new JnValue(args[i].getClass().getTypeName(), args[i]); // TODO: chequear que funcione!!!
        }

        this.arguments = new JnValues("System.Object[]", array);

    }
    public String getIdentifier() {
        return (String)identifier.$value;
    }

    public void setIdentifier(String identifier) {
        this.identifier = new JnValue("System.Guid", identifier);
    }
}
