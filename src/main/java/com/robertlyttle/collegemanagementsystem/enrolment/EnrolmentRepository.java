package com.robertlyttle.collegemanagementsystem.enrolment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;


public interface EnrolmentRepository extends JpaRepository<Enrolment, Long> {
    List<Enrolment> getEnrolmentsByCourse_CourseNo(String courseNumber);
    List<Enrolment> getEnrolmentsByStudent_StudentNumber(String studentNumber);
}
