package com.scheduleProjector.scheduleProjector.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
        name = "course",
        uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "name"})
})
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Course(String name, Integer credits, String prerequisites, User user) {
        this.name = name;
        this.credits = credits;
        this.prerequisites = prerequisites;
        this.user = user;
    }
}
