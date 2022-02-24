package com.robertlyttle.collegemanagementsystem.exception.model;

public class CourseNotFoundException extends RuntimeException {
    public CourseNotFoundException(String message) {
        super(message);
    }
}
