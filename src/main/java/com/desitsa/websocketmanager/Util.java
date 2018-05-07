package com.desitsa.websocketmanager;

 public class Util {

    /**
     * Obtiene el tipo de dato de C# según una clase de Java
     */
    public static String getCSharpType(Class javaClass) {

        switch (javaClass.getSimpleName()) {
            case "UUID":        return "System.Guid";
            case "Boolean":     return "System.Boolean";
            case "Byte":        return "System.Byte";
            case "Short":       return "System.Int16";
            case "Integer":     return "System.Int32";
            case "Long":        return "System.Int64";
            case "String":      return "System.String";
            case "Char":        return "System.Char";
            case "Float":       return "System.Single";
            case "Double":      return "System.Double";
            default:            return "System.Object";
        }
    }

    /**
     * Obtiene el tipo de dato de Java a partir de un tipo de C#
     * @return el valor casteado y su tipo correspondiente
     */
    public static Object[] convertToJavaType(String cSharpType, Object obj) {

        Object[] array = new Object[2];

        // En números se hace el casteo correspondiente al valor, porque por defecto a cualquier número llega como Double

        switch (cSharpType) {
            case "System.String":
                array[1] = String.class;
                break;
            case "System.Boolean":
                array[1] = boolean.class;
                break;
            case "System.Byte":
                array[0] = ((Double)obj).byteValue();
                array[1] = byte.class;
                break;
            case "System.Int16":
                array[0] = ((Double)obj).shortValue();
                array[1] = short.class;
                break;
            case "System.Int32":
                array[0] = ((Double)obj).intValue();
                array[1] = int.class;
                break;
            case "System.Int64":
                array[0] = ((Double)obj).longValue();
                array[1] = long.class;
                break;
            case "System.Single":
                array[0] = ((Double)obj).floatValue();
                array[1] = float.class;
                break;
            case "System.Double":
                array[1] = double.class;
                break;
            default:
                array[1] = Object.class;
                break;
        }

        // Si no se casteó.. por defecto se asigna sin castear a nada.
        if (array[0] == null) array[0] = obj;

        return array;
    }
}
