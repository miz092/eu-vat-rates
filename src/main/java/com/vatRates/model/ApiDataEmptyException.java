package com.vatRates.model;

public class ApiDataEmptyException extends RuntimeException{
    public ApiDataEmptyException(String message) {
        super(message);
    }
}
