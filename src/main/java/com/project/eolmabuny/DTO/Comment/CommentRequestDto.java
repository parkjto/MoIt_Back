package com.project.eolmabuny.DTO.Comment;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentRequestDto {

    private String postUuid;  // Long postId -> String postUuid

    private Long parentCommentId;

    private String content;
}