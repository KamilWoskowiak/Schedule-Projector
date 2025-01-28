package com.scheduleProjector.scheduleProjector.repository;

import com.scheduleProjector.scheduleProjector.model.Schedule;
import com.scheduleProjector.scheduleProjector.model.Semester;
import com.scheduleProjector.scheduleProjector.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SemesterRepository extends JpaRepository<Semester, Long> {
    List<Semester> findByUser(User user);
    Optional<Semester> findByNameAndUser(String name, User user);
}
