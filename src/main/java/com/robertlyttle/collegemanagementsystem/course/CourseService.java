package com.robertlyttle.collegemanagementsystem.course;

import com.robertlyttle.collegemanagementsystem.exception.model.CourseNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import javax.transaction.Transactional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;

    public Course addNewCourse(@RequestBody Course course) {
        Course newCourse = new Course();
        newCourse.setCourseNo(course.getCourseNo());
        newCourse.setName(course.getName());
        newCourse.setDepartment(course.getDepartment());
        newCourse.setCost(course.getCost());
        newCourse.setType(course.getType());
        newCourse.setDurationInYears(course.getDurationInYears());
        log.info("Saving new course: {} {}", newCourse.getCourseNo() ,newCourse.getName());
        return courseRepository.save(newCourse);
    }

    public Course updateCourse(String courseNumber, Course course) {
        Course currentCourse = findByCourseNumber(courseNumber);
        if (currentCourse != null) {
            currentCourse.setCourseNo(course.getCourseNo());
            currentCourse.setName(course.getName());
            currentCourse.setDepartment(course.getDepartment());
            currentCourse.setCost(course.getCost());
            currentCourse.setType(course.getType());
            currentCourse.setDurationInYears(course.getDurationInYears());
            log.info("Updating course: {} {}", currentCourse.getCourseNo() , currentCourse.getName());
            return courseRepository.save(currentCourse);
        }
        return null;
    }

    public Course findByCourseNumber(String courseNumber) {
        Course course = courseRepository.findCourseByCourseNo(courseNumber);
        if (course == null) {
            throw new CourseNotFoundException("Course " + courseNumber + " not found");
        }
        log.info("Getting course {}", courseNumber);
        return course;
    }

    public Page<Course> getAllCourses(int pageNumber) {
        log.info("Returning all courses, Page number: {}", pageNumber);
        return courseRepository.findAll(PageRequest.of(pageNumber, 10));
    }

    public boolean deleteCourse(Long id) {
        boolean courseExists = courseRepository.existsById(id);
        if (courseExists) {
            log.info("Deleting course with ID: {}", id);
            courseRepository.deleteById(id);
            return true;
        }
        return false;

    }
}
