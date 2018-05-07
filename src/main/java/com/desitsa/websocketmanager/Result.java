package com.desitsa.websocketmanager;


public class Result {

    private ResultListener resultListener;

    public void result(ResultListener resultListener) {
        this.resultListener = resultListener;
    }

    ResultListener getResultListener() {
        return resultListener;
    }
}
