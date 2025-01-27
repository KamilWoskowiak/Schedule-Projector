package com.scheduleProjector.scheduleProjector.service;

import com.scheduleProjector.scheduleProjector.dto.CourseDto;
import com.scheduleProjector.scheduleProjector.model.Course;
import com.scheduleProjector.scheduleProjector.model.User;
import com.scheduleProjector.scheduleProjector.repository.CourseRepository;
import com.scheduleProjector.scheduleProjector.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    public CourseService(
            CourseRepository courseRepository,
            UserRepository userRepository
    ) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    public void saveCourses(String email, List<CourseDto> courses) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + email));

        List<Course> existingCourses = courseRepository.findByUser(user);

        if (existingCourses.size() + courses.size() > 50) {
            throw new IllegalArgumentException("Too many courses: " + (existingCourses.size() + courses.size()));
        }

        for (CourseDto courseDto : courses) {

            if (courseDto.getName() == null || courseDto.getCredits() == null || courseDto.getPrerequisites() == null) {
                throw new IllegalArgumentException("Invalid course data: " + courseDto.getName() + " " + courseDto.getCredits() + " " + courseDto.getPrerequisites());
            }

            if (existingCourses.stream().anyMatch(c -> c.getName().equals(courseDto.getName()))) {
                throw new IllegalArgumentException("Course already exists: " + courseDto.getName());
            }

            Course course = new Course(
                    courseDto.getName(),
                    courseDto.getCredits(),
                    courseDto.getPrerequisites(),
                    user
            );

            courseRepository.save(course);

        }
    }

    public void deleteCourses(String email, List<String> courseNames) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + email));

        for (String course : courseNames) {
            courseRepository.deleteByNameAndUser(course, user);
        }

    }

}
