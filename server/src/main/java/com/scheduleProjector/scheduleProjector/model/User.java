package com.scheduleProjector.scheduleProjector.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name = "email", nullable = false, unique = true, columnDefinition = "VARCHAR(50)")
    private String email;

    @Column(name = "provider", nullable = false)
    private String provider;

    @Column(name = "providerId", nullable = false)
    private String providerId;

    @Column(name = "numberofschedules", nullable = false, columnDefinition = "SMALLINT")
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

    public User(String email, String provider, String name) {
        this.email = email;
        this.provider = provider;
        this.name = name;
        this.numberOfSchedules = 0;
    }

}
