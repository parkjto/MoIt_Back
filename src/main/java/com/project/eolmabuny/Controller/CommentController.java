package com.project.eolmabuny.Controller;

import com.project.eolmabuny.DTO.Comment.CommentResponseDto;
import com.project.eolmabuny.DTO.Comment.CommentRequestDto;
import com.project.eolmabuny.Service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 댓글 작성
    @PostMapping
    public ResponseEntity<CommentResponseDto> createComment(
            @RequestBody CommentRequestDto requestDto,
            @RequestParam Long userId) {  // <- userId를 파라미터로 받음
        CommentResponseDto responseDto = commentService.createComment(requestDto, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    // 게시글별 댓글 조회
    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentResponseDto>> getCommentsByPost(@PathVariable Long postId) {
        List<CommentResponseDto> responseDtos = commentService.getCommentsByPost(postId);
        return ResponseEntity.ok(responseDtos);
    }

    // 댓글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long id,
            @RequestParam Long userId) {
        commentService.deleteComment(id, userId);
        return ResponseEntity.noContent().build();
    }
}