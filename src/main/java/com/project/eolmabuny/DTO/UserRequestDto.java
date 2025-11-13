package com.project.eolmabuny.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequestDto {
    private Long userId;

    private String email;

    private String password;

    private String nickname;
}
