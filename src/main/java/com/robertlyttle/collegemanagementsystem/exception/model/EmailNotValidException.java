package com.robertlyttle.collegemanagementsystem.exception.model;

public class EmailNotValidException extends RuntimeException {
    public EmailNotValidException(String message) {
        super(message);
    }
}
