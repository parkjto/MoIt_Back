package com.project.eolmabuny.DTO.Post;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostResponseDto {

    private Long postId;

    private Long userId;

    private String nickname;  // 작성자 닉네임

    private String category;

    private String title;

    private String content;

    private String jobTag;

    private Integer viewCount;

    private Long likeCount;  // 좋아요 수

    private Boolean isLiked;  // 현재 사용자가 좋아요 눌렀는지

    private Long commentCount;  // 댓글 수

    private LocalDateTime createdAt;
}