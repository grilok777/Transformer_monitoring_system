package com.example.exception;

public class InvalidTokenException extends RuntimeException{
    public InvalidTokenException(String message) {
        super(message);
    }
    public InvalidTokenException() {
        super("Invalid token");
    }
}