package com.desitsa.websocketmanager;

import java.util.UUID;

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
     */
    public static Class getJavaClass(String cSharpType) {
        switch (cSharpType) {
            case "System.Guid":     return UUID.class;
            case "System.Boolean":  return boolean.class;
            case "System.Byte":     return byte.class;
            case "System.Int16":    return short.class;
            case "System.Int32":    return int.class;
            case "System.Int64":    return long.class;
            case "System.String":   return String.class;
            case "System.Char":     return char.class;
            case "System.Single":   return float.class;
            case "System.Double":   return double.class;
            default:                return Object.class;
        }
    }
}
