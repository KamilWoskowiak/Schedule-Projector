package com.scheduleProjector.scheduleProjector.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.scheduleProjector.scheduleProjector.model.Course;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<Course> findById(Long id);
    List<Course> findAllByUserId(Long userId);
}
