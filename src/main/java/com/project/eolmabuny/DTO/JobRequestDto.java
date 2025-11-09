package com.project.eolmabuny.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobRequestDto {

    private Long userId;

    private String jobName;

    private Integer hourlyWage;

    private String workplaceName;
}