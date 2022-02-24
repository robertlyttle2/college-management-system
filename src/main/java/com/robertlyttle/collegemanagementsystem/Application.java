package com.robertlyttle.collegemanagementsystem;

import com.robertlyttle.collegemanagementsystem.course.Course;
import com.robertlyttle.collegemanagementsystem.course.CourseRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.util.List;
import static com.robertlyttle.collegemanagementsystem.course.enumeration.CourseType.*;


@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(CourseRepository courseRepository) {
        return args -> {

            courseRepository.saveAll(List.of(
                    new Course("JV101", "Java Programming", "Computing", 550, FULL_TIME, 3),
                    new Course("SQL101", "SQL", "Computing", 300, PART_TIME, 2),
                    new Course("MT242", "Maths", "Finance", 550, FULL_TIME, 5)
            ));
        };

    }


}
