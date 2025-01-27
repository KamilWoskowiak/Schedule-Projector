package com.scheduleProjector.scheduleProjector.controller;

import com.scheduleProjector.scheduleProjector.dto.CourseDto;
import com.scheduleProjector.scheduleProjector.service.CourseService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping()
    public ResponseEntity<?> upload(
            @RequestBody List<CourseDto> courses,
            Authentication authentication
    ) {
        String email = (String) authentication.getPrincipal();
        try {
            courseService.saveCourses(email, courses);
            return ResponseEntity.ok("Courses saved successfully.");
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Course already exists.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping()
    public ResponseEntity<?> delete(
            @RequestBody List<String> courseNames,
            Authentication authentication
    ) {
        String email = (String) authentication.getPrincipal();
        try {
            courseService.deleteCourses(email, courseNames);
            return ResponseEntity.ok("Courses deleted successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
