package com.scheduleProjector.scheduleProjector.repository;

import com.scheduleProjector.scheduleProjector.model.Schedule;
import com.scheduleProjector.scheduleProjector.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByUser(User user);
    Optional<Schedule> findByNameAndUser(String name, User user);
}
