package com.desitsa.websocketmanager;

public class JnException {

    public JnException(String $type, Object message) {
        this.$type = $type;
        this.message = message;
    }

    public String $type;

    public Object message;
}
