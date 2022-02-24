package com.robertlyttle.collegemanagementsystem.course;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.robertlyttle.collegemanagementsystem.course.enumeration.CourseType;
import com.robertlyttle.collegemanagementsystem.enrolment.Enrolment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Course")
@Table(name = "course")
public class Course {
    @Id
    @SequenceGenerator(
            name = "course_sequence",
            sequenceName = "course_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "course_sequence"
    )
    @JsonProperty("courseId")
    private Long id;
    private String courseNo;
    private String name;
    private String department;
    private double cost;
    @Enumerated(value = EnumType.STRING)
    private CourseType type;
    private int durationInYears;
    @OneToMany(
                cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
                mappedBy = "course"
    )
    @JsonIgnore
    private List<Enrolment> enrolments = new ArrayList<>();

    public Course(String courseNo, String name, String department, double cost, CourseType type, int durationInYears) {
        this.courseNo = courseNo;
        this.name = name;
        this.department = department;
        this.cost = cost;
        this.type = type;
        this.durationInYears = durationInYears;
    }
}
