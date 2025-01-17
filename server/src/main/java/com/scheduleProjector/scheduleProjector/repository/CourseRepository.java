package com.scheduleProjector.scheduleProjector.repository;

import com.scheduleProjector.scheduleProjector.model.Course;
import com.scheduleProjector.scheduleProjector.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByUser(User user);
    Optional<Course> findByNameAndUser(String name, User user);
}
