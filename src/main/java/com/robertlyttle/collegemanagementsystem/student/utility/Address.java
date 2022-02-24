package com.robertlyttle.collegemanagementsystem.student.utility;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class Address {
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String postCode;
}
