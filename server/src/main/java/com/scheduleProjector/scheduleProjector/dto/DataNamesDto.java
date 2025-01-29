package com.scheduleProjector.scheduleProjector.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class DataNamesDto {
    private String[] semesterNames;
    private String[] courseNames;
}
