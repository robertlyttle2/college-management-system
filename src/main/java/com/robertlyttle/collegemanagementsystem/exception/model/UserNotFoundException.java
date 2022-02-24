package com.robertlyttle.collegemanagementsystem.exception.model;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String message) {
        super(message);
    }
}
