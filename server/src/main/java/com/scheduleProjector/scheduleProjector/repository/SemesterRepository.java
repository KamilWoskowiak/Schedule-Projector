package com.scheduleProjector.scheduleProjector.repository;

import com.scheduleProjector.scheduleProjector.model.Schedule;
import com.scheduleProjector.scheduleProjector.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SemesterRepository {
    List<Schedule> findByUser(User user);
}
