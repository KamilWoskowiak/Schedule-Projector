package com.scheduleProjector.scheduleProjector.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", nullable = false, unique = true, columnDefinition = "VARCHAR(50)")
    private String email;

    @Column(name = "provider", nullable = false)
    private String provider;

    @Column(name = "numberofschedules", columnDefinition = "SMALLINT")
    private Integer numberOfSchedules;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Course> courses = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Schedule> schedules = new ArrayList<>();

    @Lob
    @Column(name = "semesters", columnDefinition = "TEXT")
    private String semesters;

    public User() {
    }

    public User(String email, String provider) {
        this.email = email;
        this.provider = provider;
        this.numberOfSchedules = 0;
    }
}
