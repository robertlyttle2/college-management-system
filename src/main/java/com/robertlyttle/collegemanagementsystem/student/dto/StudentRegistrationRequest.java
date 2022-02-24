package com.robertlyttle.collegemanagementsystem.student.dto;

import com.robertlyttle.collegemanagementsystem.student.utility.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class StudentRegistrationRequest {
    private String firstName;
    private String lastName;
    private String email;
    private Address address;
    private String contactNumber;
    private String courseNumber;
}
