package com.example.finalproject.handler;

import java.util.ArrayList;
import java.util.List;

public class ExceptionDto {

    private final int code;
    private final String message;
    private final List<String> details = new ArrayList<>();

    public ExceptionDto(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public void addDetail(String detail) {
        details.add(detail);
    }

    public List<String> getDetails() {
        return details;
    }
}
