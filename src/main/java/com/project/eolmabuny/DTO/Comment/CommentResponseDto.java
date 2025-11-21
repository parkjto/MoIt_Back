package com.project.eolmabuny.DTO.Comment;

import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentResponseDto {

    private Long commentId;

    private Long userId;

    private String nickname;

    private String postUuid;  // Long postId -> String postUuid

    private Long parentCommentId;

    private String content;

    private LocalDateTime createdAt;

    private List<CommentResponseDto> replies;
}