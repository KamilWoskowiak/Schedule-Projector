package com.scheduleProjector.scheduleProjector.repository;

import com.scheduleProjector.scheduleProjector.model.ScheduleItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ScheduleItemRepository extends JpaRepository<ScheduleItem, Long> {
    Optional<ScheduleItem> findByScheduleIdAndCourseId(Long scheduleId, Long courseId);
    List<ScheduleItem> findAllByScheduleId(Long scheduleId);
}
