package com.scheduleProjector.scheduleProjector.model;

import com.scheduleProjector.scheduleProjector.dto.SemesterDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "semester")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Semester {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, columnDefinition = "VARCHAR(50)")
    private String name;

    @Column(name = "max_credits", nullable = false, columnDefinition = "SMALLINT")
    private Integer maxCredits;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(mappedBy = "semesters") // Matches the field name in Course
    private List<Course> courses = new ArrayList<>();

    public Semester(String name, Integer maxCredits, User user) {
        this.name = name;
        this.maxCredits = maxCredits;
        this.user = user;
    }

    public SemesterDto toDto() {
        return new SemesterDto(name, maxCredits);
    }

}
