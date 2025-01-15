package com.scheduleProjector.scheduleProjector.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.scheduleProjector.scheduleProjector.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
