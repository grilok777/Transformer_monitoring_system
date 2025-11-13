package com.example.exception;

public class EmailAlreadyExistException extends RuntimeException {
    public EmailAlreadyExistException(String message) {
        super(message);
    }

    public EmailAlreadyExistException(){
        super("Email already exist");
    }
}