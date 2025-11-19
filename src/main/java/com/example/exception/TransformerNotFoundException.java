package com.example.exception;

public class TransformerNotFoundException extends RuntimeException{
    public TransformerNotFoundException(){
        super("Transformer not found");
    }
    public TransformerNotFoundException(String msg){
        super(msg);
    }
}