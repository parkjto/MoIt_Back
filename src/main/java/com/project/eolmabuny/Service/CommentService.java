package com.project.eolmabuny.Service;

import com.project.eolmabuny.DTO.Comment.CommentRequestDto;
import com.project.eolmabuny.DTO.Comment.CommentResponseDto;
import com.project.eolmabuny.Model.Entity.Comment;
import com.project.eolmabuny.Model.Entity.Post;
import com.project.eolmabuny.Model.Entity.User;
import com.project.eolmabuny.Model.Repository.CommentRepository;
import com.project.eolmabuny.Model.Repository.PostRepository;
import com.project.eolmabuny.Model.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    // 댓글 작성 (댓글 or 대댓글)
    @Transactional
    public CommentResponseDto createComment(CommentRequestDto requestDto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        Post post = postRepository.findById(requestDto.getPostUuid())  // String postUuid
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        Comment parentComment = null;
        if (requestDto.getParentCommentId() != null) {
            parentComment = commentRepository.findById(requestDto.getParentCommentId())
                    .orElseThrow(() -> new IllegalArgumentException("상위 댓글을 찾을 수 없습니다."));
        }

        Comment comment = Comment.builder()
                .user(user)
                .post(post)
                .parentComment(parentComment)
                .content(requestDto.getContent())
                .build();

        Comment savedComment = commentRepository.save(comment);
        return convertToResponseDto(savedComment);
    }

    // 게시글별 댓글 조회 (대댓글 포함)
    public List<CommentResponseDto> getCommentsByPost(String postUuid) {  // Long -> String
        // 최상위 댓글만 조회
        List<Comment> comments = commentRepository
                .findByPost_PostUuidAndParentCommentIsNullOrderByCreatedAtAsc(postUuid);

        return comments.stream()
                .map(this::convertToResponseDtoWithReplies)
                .collect(Collectors.toList());
    }

    // 댓글 삭제
    @Transactional
    public void deleteComment(Long commentId, Long userId) {
        Comment comment = commentRepository.findByCommentIdAndUser_UserId(commentId, userId)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없거나 삭제 권한이 없습니다."));

        commentRepository.delete(comment);
    }

    // Entity -> DTO 변환 (대댓글 포함)
    private CommentResponseDto convertToResponseDtoWithReplies(Comment comment) {
        List<CommentResponseDto> replies = comment.getReplies().stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());

        return CommentResponseDto.builder()
                .commentId(comment.getCommentId())
                .userId(comment.getUser().getUserId())
                .nickname(comment.getUser().getNickname())
                .postUuid(comment.getPost().getPostUuid())
                .parentCommentId(comment.getParentComment() != null ?
                        comment.getParentComment().getCommentId() : null)
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .replies(replies)
                .build();
    }

    // Entity -> DTO 변환 (단순)
    private CommentResponseDto convertToResponseDto(Comment comment) {
        return CommentResponseDto.builder()
                .commentId(comment.getCommentId())
                .userId(comment.getUser().getUserId())
                .nickname(comment.getUser().getNickname())
                .postUuid(comment.getPost().getPostUuid())
                .parentCommentId(comment.getParentComment() != null ?
                        comment.getParentComment().getCommentId() : null)
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .build();
    }
}