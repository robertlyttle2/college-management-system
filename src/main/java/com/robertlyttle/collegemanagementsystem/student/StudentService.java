package com.robertlyttle.collegemanagementsystem.student;

import com.robertlyttle.collegemanagementsystem.course.Course;
import com.robertlyttle.collegemanagementsystem.course.CourseService;
import com.robertlyttle.collegemanagementsystem.email.EmailService;
import com.robertlyttle.collegemanagementsystem.enrolment.Enrolment;
import com.robertlyttle.collegemanagementsystem.enrolment.EnrolmentRepository;
import com.robertlyttle.collegemanagementsystem.appuser.AppUserService;
import com.robertlyttle.collegemanagementsystem.exception.model.ContactNumberNotValidException;
import com.robertlyttle.collegemanagementsystem.exception.model.EmailAlreadyRegisteredException;
import com.robertlyttle.collegemanagementsystem.exception.model.EmailNotValidException;
import com.robertlyttle.collegemanagementsystem.exception.model.UserNotFoundException;
import com.robertlyttle.collegemanagementsystem.student.utility.Address;
import com.robertlyttle.collegemanagementsystem.validation.ContactNumberValidator;
import com.robertlyttle.collegemanagementsystem.validation.EmailValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Date;
import static com.robertlyttle.collegemanagementsystem.appuser.enumeration.AppUserRole.ROLE_STUDENT;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final EnrolmentRepository enrolmentRepository;
    private final AppUserService appUserService;
    private final CourseService courseService;
    private final EmailService emailService;
    private final BCryptPasswordEncoder passwordEncoder;

    public Student register(String firstName, String lastName, String email, Address address,
                            String contactNumber, String courseNumber) throws MessagingException {

        if (validateRegistration(firstName, lastName, email, contactNumber)) {

            String password = appUserService.generatePassword();
            String collegeNumber = appUserService.generateCollegeNumber();
            String collegeEmail = appUserService.generateCollegeEmail(firstName, lastName);

            Student newStudent = new Student();
            newStudent.setStudentNumber(collegeNumber);
            newStudent.setUsername(collegeEmail);
            newStudent.setPassword(passwordEncoder.encode(password));
            newStudent.setFirstName(StringUtils.capitalize(firstName));
            newStudent.setLastName(StringUtils.capitalize(lastName));
            newStudent.setPersonalEmail(email.toLowerCase());
            newStudent.setCollegeEmail(collegeEmail);
            newStudent.setAddress(address);
            newStudent.setContactNumber(contactNumber);
            newStudent.setJoinDate(new Date());
            newStudent.setRole(ROLE_STUDENT.name());
            newStudent.setAuthorities(ROLE_STUDENT.getAuthorities());
            newStudent.setEnabled(true);
            newStudent.setNonLocked(true);
            log.info("Registering new student: {} {}", newStudent.getFirstName() + " " + newStudent.getLastName(), newStudent.getStudentNumber());
            log.info("Student password is: " + password); // Will delete - only used for testing purposes
            studentRepository.save(newStudent);
            emailService.sendEmail(email, newStudent.getFirstName(), newStudent.getUsername(), password);
            enrol(newStudent.getStudentNumber(), courseNumber);
            return newStudent;
        }
        return null;
    }

    public Student update(String studentNumber, String email, Address address, String contactNumber) {
        Student existingStudent = findByStudentNumber(studentNumber);

        if (existingStudent != null
                && validateEmail(studentNumber, email)
                && validateContactNumber(contactNumber)) {
            existingStudent.setPersonalEmail(email);
            existingStudent.setAddress(address);
            existingStudent.setContactNumber(contactNumber);
            log.info("Updating student: {}", existingStudent.getFirstName() + " " + existingStudent.getLastName());
            return studentRepository.save(existingStudent);
        }
        return null;
    }

    public void enrol(String studentNumber, String courseNumber) {
        Student student = findByStudentNumber(studentNumber);
        Course course = courseService.findByCourseNumber(courseNumber);

        if (student != null && course != null) {
            Enrolment enrolment = new Enrolment(student, course, LocalDateTime.now());
            student.getEnrolments().add(enrolment);
            student.setBalance(course.getCost());
            log.info("Enrolling student {} in course {}", student.getStudentNumber(), course.getCourseNo());
            enrolmentRepository.save(enrolment);
            studentRepository.save(student);
        }
    }

    public Student findByStudentNumber(String studentNumber) {
        Student student = studentRepository.findStudentByStudentNumber(studentNumber);
        if (student == null) {
            throw new UserNotFoundException("Student with student number '" + studentNumber + "' not found");
        }
        log.info("Getting student by student number: {}", studentNumber);
        return student;
    }

    public Page<Student> getAllStudents(int pageNumber) {
        log.info("Getting all students from the database - Page number: {}", pageNumber);
        return studentRepository.findAll(PageRequest.of(pageNumber, 10));
    }

    public Student getStudent(Long id) {
        log.info("Getting student with ID: {}", id);
        return studentRepository.findById(id).orElseThrow();
    }

    public boolean delete(Long id) {
        boolean studentExists = studentRepository.existsById(id);
        if (studentExists) {
            log.info("Deleting student with ID: {}", id);
            studentRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private boolean validateEmail(String studentNumber, String email) {
        final String EMAIL_BLANK_MESSAGE = "Email address cannot be blank. Please enter a valid email address";
        final String EMAIL_INVALID_MESSAGE = "Invalid email. Please enter a valid email address";
        final String EMAIL_IN_USE_MESSAGE = "Email address is already in use. Please use a different email";

        if (StringUtils.isEmpty(email)) {
            throw new EmailNotValidException(EMAIL_BLANK_MESSAGE);
        }

        if (!EmailValidator.isValid(email)) {
            log.info(EMAIL_INVALID_MESSAGE);
            throw new EmailNotValidException(EMAIL_INVALID_MESSAGE);
        }

        Student existingStudent = studentRepository.findStudentByPersonalEmail(email);
        if (existingStudent != null && !existingStudent.getStudentNumber().equals(studentNumber)) {
            throw new EmailAlreadyRegisteredException(EMAIL_IN_USE_MESSAGE);
        }

        return true;
    }

    private boolean validateContactNumber(String contactNumber) {
        final String BLANK_MESSAGE = "Contact number cannot be blank. Please enter a valid contact number";
        final String INVALID_MESSAGE = "Invalid contact number. Please use a different number";

        if (StringUtils.isEmpty(contactNumber)) {
            log.info(BLANK_MESSAGE);
            throw new ContactNumberNotValidException(BLANK_MESSAGE);
        }

        if (!ContactNumberValidator.isValid(contactNumber)) {
            log.info(INVALID_MESSAGE);
            throw new ContactNumberNotValidException(INVALID_MESSAGE);
        }

        return true;
    }

    private boolean validateRegistration(String firstName, String lastName, String email, String contactNumber) {
        return appUserService.validateName(firstName, lastName)
                && validateEmail(null, email)
                && validateContactNumber(contactNumber);
    }

}

