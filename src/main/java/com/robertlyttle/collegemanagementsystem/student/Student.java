package com.robertlyttle.collegemanagementsystem.student;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.robertlyttle.collegemanagementsystem.enrolment.Enrolment;
import com.robertlyttle.collegemanagementsystem.appuser.AppUser;
import com.robertlyttle.collegemanagementsystem.student.utility.Address;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Student")
@Table(name = "student")
public class Student extends AppUser {

    private String studentNumber;
    private String personalEmail;
    private String collegeEmail;
    @Embedded
    private Address address;
    private String contactNumber;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date joinDate;
    private Date leaveDate;
    private double balance = 0;

    @OneToMany(
            mappedBy = "student",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE}
    )
    private List<Enrolment> enrolments = new ArrayList<>();
}
