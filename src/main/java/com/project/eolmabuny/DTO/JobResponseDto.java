package com.project.eolmabuny.DTO;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobResponseDto {

    private Long jobId;

    private Long userId;

    private String jobName;

    private Integer hourlyWage;

    private String workplaceName;

    private LocalDateTime createdAt;
}