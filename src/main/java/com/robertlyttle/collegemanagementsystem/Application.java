package com.robertlyttle.collegemanagementsystem;

import com.robertlyttle.collegemanagementsystem.appuser.AppUserService;
import com.robertlyttle.collegemanagementsystem.appuser.enumeration.AppUserRole;
import com.robertlyttle.collegemanagementsystem.course.Course;
import com.robertlyttle.collegemanagementsystem.course.CourseRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.util.List;

import static com.robertlyttle.collegemanagementsystem.appuser.enumeration.AppUserRole.*;
import static com.robertlyttle.collegemanagementsystem.course.enumeration.CourseType.*;


@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(CourseRepository courseRepository, AppUserService appUserService) {
        return args -> {

//            appUserService.addNewUser("Super", "Admin", ROLE_SUPER_ADMIN);
//            appUserService.addNewUser("Course", "Admin", ROLE_COURSE_ADMIN);
//            appUserService.addNewUser("Enrolment", "Admin", ROLE_ENROLMENT_ADMIN);
//
//            courseRepository.saveAll(List.of(
//                    new Course("JV101", "Java Programming", "Computing", 550, FULL_TIME, 3),
//                    new Course("SQL101", "SQL", "Computing", 300, PART_TIME, 2),
//                    new Course("MT242", "Maths", "Finance", 550, FULL_TIME, 5)
//            ));
        };

    }


}
