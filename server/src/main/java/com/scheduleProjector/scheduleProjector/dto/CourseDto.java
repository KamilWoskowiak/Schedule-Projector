package com.scheduleProjector.scheduleProjector.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class CourseDto {
    private String name;
    private Integer credits;
    private String prerequisites;
    private String[] semesters;
}
