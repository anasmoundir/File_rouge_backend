package com.example.demo.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)

public class CustomDependencyException extends RuntimeException{

    public CustomDependencyException(String message) {
        super(message);
    }
}
