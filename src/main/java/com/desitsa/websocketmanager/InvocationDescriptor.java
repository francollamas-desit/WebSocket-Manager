package com.desitsa.websocketmanager;

/**
 * Representa a una invocación de un método
 */
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


    /**
     * Obtiene los argumentos con sus tipos.
     */
    public Object[] getArguments() {

        // array de {Argumentos, Tipos}
        Object[] array = new Object[2];

        Object[] argsValue = new Object[arguments.$values.length];
        Class[] argsType = new Class[arguments.$values.length];

        array[0] = argsValue;
        array[1] = argsType;

        for (int i = 0; i < arguments.$values.length; i++) {
            Object[] res = Util.convertToJavaType(arguments.$values[i].$type, arguments.$values[i].$value);
            argsValue[i] = res[0];
            argsType[i] = (Class) res[1];
        }
        return array;
    }

    /**
     * Setea los argumentos
     */
    public void setArguments(Object[] args) {
        JnValue[] array = new JnValue[args.length];
        for (int i = 0; i < args.length; i++) {
            array[i] = new JnValue(Util.getCSharpType(args[i].getClass()), args[i]);
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
