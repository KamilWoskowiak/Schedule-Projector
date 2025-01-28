package com.scheduleProjector.scheduleProjector.service;

import com.scheduleProjector.scheduleProjector.dto.BulkDataDto;
import com.scheduleProjector.scheduleProjector.dto.CourseDto;
import com.scheduleProjector.scheduleProjector.dto.SemesterDto;
import com.scheduleProjector.scheduleProjector.model.Course;
import com.scheduleProjector.scheduleProjector.model.Semester;
import com.scheduleProjector.scheduleProjector.model.User;
import com.scheduleProjector.scheduleProjector.repository.CourseRepository;
import com.scheduleProjector.scheduleProjector.repository.SemesterRepository;
import com.scheduleProjector.scheduleProjector.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DataHandlerService {

    private final CourseRepository courseRepository;
    private final SemesterRepository semesterRepository;
    private final UserRepository userRepository;

    public DataHandlerService(CourseRepository courseRepository, SemesterRepository semesterRepository, UserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.semesterRepository = semesterRepository;
        this.userRepository = userRepository;
    }

    public void handleData(BulkDataDto data , String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + email));

        Map<String, Semester> semesterMap = processSemesters(data.getSemesters(), user);

        if (semesterMap.isEmpty()) {
            throw new IllegalArgumentException("No semesters provided");
        }

        processCourses(data.getCourses(), semesterMap, user);

    }

    private Map<String, Semester> processSemesters(List<SemesterDto> semesters, User user) {

        Map<String, Semester> semesterMap = new HashMap<>();

        for (SemesterDto semester : semesters) {
            if (semester.getName() == null || semester.getName().isBlank() || semester.getMaxCredits() <= 0) {
                throw new IllegalArgumentException("Invalid semester: " + semester);
            }
        }

        for (SemesterDto semester : semesters) {
            semesterRepository.findByNameAndUser(semester.getName(), user)
                    .ifPresentOrElse(
                            existingSemester -> semesterMap.put(semester.getName(), existingSemester),
                            () -> {
                                Semester input = new Semester(semester.getName(), semester.getMaxCredits(), user);
                                semesterRepository.save(input);
                                semesterMap.put(semester.getName(), input);
                            }
            );
        }
        return semesterMap;
    }

    private void processCourses(List<CourseDto> courses, Map<String, Semester> semesterMap, User user) {

        for (CourseDto course : courses) {
            if (course.getName() == null || course.getName().isBlank() || course.getCredits() <= 0) {
                throw new IllegalArgumentException("Invalid course: " + course);
            }
        }

        for (CourseDto course : courses) {
            courseRepository.findByNameAndUser(course.getName(), user)
                    .ifPresentOrElse(
                            existingCourse -> {
                                existingCourse.setName(course.getName());
                                existingCourse.setCredits(course.getCredits());
                                existingCourse.setPrerequisites(course.getPrerequisites());
                                for (String semester : course.getSemesters()) {
                                    existingCourse.addSemester(semesterMap.get(semester));
                                }
                                courseRepository.save(existingCourse);
                            },
                            () -> {
                                Course input = new Course(course.getName(), course.getCredits(), course.getPrerequisites(), user);
                                for (String semester : course.getSemesters()) {
                                    input.addSemester(semesterMap.get(semester));
                                }
                                courseRepository.save(input);
                            }
                    );
        }

    }



}
