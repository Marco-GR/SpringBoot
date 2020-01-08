package com.example.myFirstSpringApp.exception;

public class CustomRunTimeException extends RuntimeException {

    public CustomRunTimeException(String message){
        super(message);
    }

}
