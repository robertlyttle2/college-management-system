package com.robertlyttle.collegemanagementsystem.enrolment;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/v1/enrolment")
@RequiredArgsConstructor
public class EnrolmentController {

    private final EnrolmentService enrolmentService;

    @GetMapping("/all")
    public List<Enrolment> getAllEnrolments() {
        return enrolmentService.getAllEnrolments();
    }

    @GetMapping("courseNumber")
    public List<Enrolment> getEnrolmentsByCourseNumber(@RequestParam String courseNumber) {
        return enrolmentService.getEnrolmentsByCourseNumber(courseNumber);
    }
    @GetMapping("studentNumber")
    public List<Enrolment> getEnrolmentsByStudentNumber(@RequestParam String studentNumber) {
        return enrolmentService.getEnrolmentsByStudentNumber(studentNumber);
    }

}
