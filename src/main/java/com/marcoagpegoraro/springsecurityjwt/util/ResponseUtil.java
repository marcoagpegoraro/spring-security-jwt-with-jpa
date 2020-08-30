package com.marcoagpegoraro.springsecurityjwt.util;

public class ResponseUtil<T> {
    private String message;
    private T response;

    public ResponseUtil(String message, T response) {
        this.message = message;
        this.response = response;
    }

    public ResponseUtil() {
    }

    public ResponseUtil(String message) {
        this.message = message;
        response = null;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }
}
