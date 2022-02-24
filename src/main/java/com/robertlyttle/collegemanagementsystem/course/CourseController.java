package com.robertlyttle.collegemanagementsystem.course;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1/course")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @PostMapping("/add")
    public Course saveCourse(@RequestBody Course course) {
        return courseService.addNewCourse(course);
    }

    @PutMapping("/update/{courseNumber}")
    public Course updateCourse(@PathVariable("courseNumber") String courseNumber,
                               @RequestBody Course course) {
        return courseService.updateCourse(courseNumber, course);
    }

    @GetMapping("/find/{courseNumber}")
    public Course findCourse(@PathVariable("courseNumber") String courseNumber) {
        return courseService.findByCourseNumber(courseNumber);
    }

    @GetMapping("/find/all/{pageNumber}")
    public Page<Course> getAllCourses(@PathVariable("pageNumber") int pageNumber) {
        return courseService.getAllCourses(pageNumber);
    }

    @DeleteMapping("/delete/{courseId}")
    public boolean deleteCourse(@PathVariable("courseId") Long id) {
        return courseService.deleteCourse(id);
    }
}
