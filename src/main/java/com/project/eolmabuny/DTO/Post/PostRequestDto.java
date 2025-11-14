package com.project.eolmabuny.DTO.Post;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostRequestDto {

    //private Long userId;

    private String category;

    private String title;

    private String content;

    private String jobTag;
}