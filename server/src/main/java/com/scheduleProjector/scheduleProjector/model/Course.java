package com.scheduleProjector.scheduleProjector.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "course",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_id", "name"})
        })
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, columnDefinition = "VARCHAR(50)")
    private String name;

    @Column(name = "credits", nullable = false, columnDefinition = "SMALLINT")
    private Integer credits;

    @Column(name = "prerequisites", columnDefinition = "TEXT")
    private String prerequisites;

    @ManyToMany
    @JoinTable(
            name = "course_semester",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "semester_id")
    )
    private List<Semester> semesters = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Course(String name, Integer credits, String prerequisites, User user) {
        this.name = name;
        this.credits = credits;
        this.prerequisites = prerequisites;
        this.user = user;
    }

    public void addSemester(Semester semester) {
        this.semesters.add(semester);
        semester.getCourses().add(this);
    }

    public void removeSemester(Semester semester) {
        this.semesters.remove(semester);
        semester.getCourses().remove(this);
    }
}