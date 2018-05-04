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


    /**
     * Obtiene los argumentos con sus tipos.
     */
    public Object[] getArguments() {

        Object[] array = new Object[2];

        Object[] argsValue = new Object[arguments.$values.length];
        Class[] argsType = new Class[arguments.$values.length];

        array[0] = argsValue;
        array[1] = argsType;

        for (int i = 0; i < arguments.$values.length; i++) {

            Object v = arguments.$values[i].$value;
            switch (arguments.$values[i].$type) {
                case "System.String":
                    argsType[i] = String.class;
                    break;
                case "System.Boolean":
                    argsType[i] = boolean.class;
                    break;
                case "System.Byte":
                    argsValue[i] = ((Double)v).byteValue();
                    argsType[i] = byte.class;
                    break;
                case "System.Int16":
                    argsValue[i] = ((Double)v).shortValue();
                    argsType[i] = short.class;
                    break;
                case "System.Int32":
                    argsValue[i] = ((Double)v).intValue();
                    argsType[i] = int.class;
                    break;
                case "System.Int64":
                    argsValue[i] = ((Double)v).longValue();
                    argsType[i] = long.class;
                    break;
                case "System.Single":
                    argsValue[i] = ((Double)v).floatValue();
                    argsType[i] = float.class;
                    break;
                case "System.Double":
                    argsType[i] = double.class;
                    break;
                default:
                    argsType[i] = Object.class;
                    break;
            }

            // Si no se casteó.. por defecto se asigna sin castear a nada.
            if (argsValue[i] == null) argsValue[i] = v;
        }
        return array;
    }

    /**
     *
     * @param args
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
