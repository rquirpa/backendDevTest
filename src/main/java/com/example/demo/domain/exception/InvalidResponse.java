package com.example.demo.domain.exception;

public class InvalidResponse extends RuntimeException {

    public InvalidResponse(String message) {
        super(message);
    }

}
