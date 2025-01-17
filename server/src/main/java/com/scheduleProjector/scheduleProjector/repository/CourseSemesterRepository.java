package com.scheduleProjector.scheduleProjector.repository;

import com.scheduleProjector.scheduleProjector.model.Course;
import com.scheduleProjector.scheduleProjector.model.CourseSemester;
import com.scheduleProjector.scheduleProjector.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseSemesterRepository extends JpaRepository<CourseSemester, Long> {
    List<CourseSemester> findBySchedule(Schedule schedule);
    Optional<CourseSemester> findByScheduleAndCourse(Schedule schedule, Course course);
}
