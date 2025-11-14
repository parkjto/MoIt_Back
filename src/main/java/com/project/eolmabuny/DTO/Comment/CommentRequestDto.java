package com.project.eolmabuny.DTO.Comment;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentRequestDto {

    //private Long userId;

    private Long postId;

    private Long parentCommentId;  // null이면 댓글, 값이 있으면 대댓글

    private String content;
}