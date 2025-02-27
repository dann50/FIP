package com.example.week7.handler;

import java.util.Map;

public class ExceptionResponse {

    private String message;
    private Map<String, String> error;

    public ExceptionResponse(String message, Map<String, String> error) {
        this.message = message;
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, String> getError() {
        return error;
    }

    public void setError(Map<String, String> error) {
        this.error = error;
    }
}
