package com.scheduleProjector.scheduleProjector.model;

import jakarta.persistence.*;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "courses")
@Getter @Setter
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "varchar(50)")
    private String name;

    @Column(nullable = false, columnDefinition = "SMALLINT")
    private int credits;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Course() {}

    public Course(String name, int credits, User user) {
        this.name = name;
        this.credits = credits;
        this.user = user;
    }

}
