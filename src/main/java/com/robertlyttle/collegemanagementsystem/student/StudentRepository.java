package com.robertlyttle.collegemanagementsystem.student;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findStudentByPersonalEmail(String email);
    Student findStudentByStudentNumber(String studentNumber);
}
