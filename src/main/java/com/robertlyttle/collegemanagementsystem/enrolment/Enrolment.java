package com.robertlyttle.collegemanagementsystem.enrolment;

import com.fasterxml.jackson.annotation.*;
import com.robertlyttle.collegemanagementsystem.course.Course;
import com.robertlyttle.collegemanagementsystem.student.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Enrolment")
@Table(name = "enrolment")
public class Enrolment {
    @Id
    @SequenceGenerator(
            name = "enrolment_sequence",
            sequenceName = "enrolment_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "enrolment_sequence"
    )
    @JsonProperty("enrolmentId")
    private Long id;

    @ManyToOne
    @JoinColumn(
            name = "student_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "enrolment_student_id_fk"
            )
    )
    @JsonIncludeProperties(
            {"studentId", "firstName", "lastName"}
    )
    private Student student;

    @ManyToOne
    @JoinColumn(
            name = "course_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "enrolment_course_id_fk"
            )
    )
    @JsonIncludeProperties(
            {"courseNo", "name", "type"}
    )
    private Course course;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime createdAt;

    public Enrolment(Student student, Course course, LocalDateTime createdAt) {
        this.student = student;
        this.course = course;
        this.createdAt = createdAt;
    }


}
