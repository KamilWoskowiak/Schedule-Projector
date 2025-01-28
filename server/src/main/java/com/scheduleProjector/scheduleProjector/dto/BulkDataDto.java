package com.scheduleProjector.scheduleProjector.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class BulkDataDto {
    private List<CourseDto> courses;
    private List<SemesterDto> semesters;
}
