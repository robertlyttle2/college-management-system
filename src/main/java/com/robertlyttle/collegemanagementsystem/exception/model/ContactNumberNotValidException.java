package com.robertlyttle.collegemanagementsystem.exception.model;

public class ContactNumberNotValidException extends RuntimeException {
    public ContactNumberNotValidException(String message) {
        super(message);
    }
}
