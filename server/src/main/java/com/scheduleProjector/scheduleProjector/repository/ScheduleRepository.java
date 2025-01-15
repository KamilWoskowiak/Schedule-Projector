package com.scheduleProjector.scheduleProjector.repository;
import com.scheduleProjector.scheduleProjector.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    Optional<Schedule> findByNameAndUserId(String name, Long userId);
    List<Schedule> findAllByUserId(Long userId);
}
