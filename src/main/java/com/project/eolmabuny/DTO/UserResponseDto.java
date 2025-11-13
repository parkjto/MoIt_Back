package com.project.eolmabuny.DTO;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDto {
    private Long userId;

    private String email;

    private String nickname;

    private LocalDateTime createdAt;
}
