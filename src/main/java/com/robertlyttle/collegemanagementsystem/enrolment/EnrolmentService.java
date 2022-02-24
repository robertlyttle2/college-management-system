package com.robertlyttle.collegemanagementsystem.enrolment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class EnrolmentService {

    private final EnrolmentRepository enrolmentRepository;

    public List<Enrolment> getAllEnrolments() {
        return enrolmentRepository.findAll();
    }

    public List<Enrolment> getEnrolmentsByCourseNumber(String courseNumber) {
        return enrolmentRepository.getEnrolmentsByCourse_CourseNo(courseNumber);
    }

    public List<Enrolment> getEnrolmentsByStudentNumber(String studentNumber) {
        return enrolmentRepository.getEnrolmentsByStudent_StudentNumber(studentNumber);
    }

}
