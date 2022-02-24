package com.robertlyttle.collegemanagementsystem.course;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Course findCourseByCourseNo(String courseNumber);
}
