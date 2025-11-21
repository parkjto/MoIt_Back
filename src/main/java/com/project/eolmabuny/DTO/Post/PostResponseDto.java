package com.project.eolmabuny.DTO;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostResponseDto {

    private String postUuid;  // Long postId -> String postUuid

    private Long userId;

    private String nickname;

    private String category;

    private String title;

    private String content;

    private String jobTag;

    private Integer viewCount;

    private Long likeCount;

    private Boolean isLiked;

    private Long commentCount;

    private LocalDateTime createdAt;
}