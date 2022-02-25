package com.robertlyttle.collegemanagementsystem.course;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/course")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @PreAuthorize("hasAnyAuthority('course:create')")
    @PostMapping("/add")
    public Course saveCourse(@RequestBody Course course) {
        return courseService.addNewCourse(course);
    }

    @PreAuthorize("hasAnyAuthority('course:update')")
    @PutMapping("/update/{courseNumber}")
    public Course updateCourse(@PathVariable("courseNumber") String courseNumber,
                               @RequestBody Course course) {
        return courseService.updateCourse(courseNumber, course);
    }

    @GetMapping("/get/{courseNumber}")
    public Course findCourse(@PathVariable("courseNumber") String courseNumber) {
        return courseService.findByCourseNumber(courseNumber);
    }

    @GetMapping("/get/all/{pageNumber}")
    public Page<Course> getAllCourses(@PathVariable("pageNumber") int pageNumber) {
        return courseService.getAllCourses(pageNumber);
    }

    @PreAuthorize("hasAnyAuthority('course:delete')")
    @DeleteMapping("/delete/{courseId}")
    public boolean deleteCourse(@PathVariable("courseId") Long id) {
        return courseService.deleteCourse(id);
    }
}
