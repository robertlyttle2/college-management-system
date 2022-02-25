package com.robertlyttle.collegemanagementsystem.student;

import com.robertlyttle.collegemanagementsystem.student.dto.StudentRegistrationRequest;
import com.robertlyttle.collegemanagementsystem.student.dto.StudentUpdateRequest;
import com.robertlyttle.collegemanagementsystem.student.utility.Address;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.mail.MessagingException;


@RestController
@RequestMapping("api/v1/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping("/get/all/{pageNumber}")
    public Page<Student> getAllStudents(@PathVariable("pageNumber") int pageNumber) {
        return studentService.getAllStudents(pageNumber);
    }

    @GetMapping("/get/{studentId}")
    public Student getStudent(@PathVariable("studentId") Long id) {
        return studentService.getStudent(id);
    }

    @GetMapping()
    public Student findByStudentNumber(@RequestParam String studentNumber) {
        return studentService.findByStudentNumber(studentNumber);
    }

    @PostMapping("/register")
    public Student register(@RequestBody StudentRegistrationRequest request) throws MessagingException {
        return studentService.register(
                request.getFirstName(),
                request.getLastName(),
                request.getEmail(),
                new Address(request.getAddress().getAddressLine1(),
                        request.getAddress().getAddressLine2(),
                        request.getAddress().getCity(),
                        request.getAddress().getPostCode()),
                request.getContactNumber(),
                request.getCourseNumber());
    }

    @PreAuthorize("hasAnyAuthority('student:update')")
    @PutMapping("/update/{studentNumber}")
    public Student update(@PathVariable("studentNumber") String studentNumber, @RequestBody StudentUpdateRequest request) {
        return studentService.update(
                studentNumber,
                request.getEmail(),
                new Address(
                        request.getAddress().getAddressLine1(),
                        request.getAddress().getAddressLine2(),
                        request.getAddress().getCity(),
                        request.getAddress().getPostCode()),
                request.getContactNumber());
    }

    @PostMapping("/enrol/{studentNumber}/{courseNumber}")
    public void enrol(@PathVariable("studentNumber") String studentNumber, @PathVariable("courseNumber") String courseNumber) {
        studentService.enrol(studentNumber, courseNumber);
    }

    @PreAuthorize("hasAnyAuthority('student:delete')")
    @DeleteMapping("/{studentId}")
    public boolean delete(@PathVariable("studentId") Long id) {
        return studentService.delete(id);
    }
}
