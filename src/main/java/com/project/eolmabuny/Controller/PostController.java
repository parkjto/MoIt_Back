package com.project.eolmabuny.Controller;

import com.project.eolmabuny.DTO.Post.PostRequestDto;
import com.project.eolmabuny.DTO.Post.PostResponseDto;
import com.project.eolmabuny.Service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // 게시글 작성
    @PostMapping
    public ResponseEntity<PostResponseDto> createPost(
            @RequestBody PostRequestDto requestDto,
            @RequestParam Long userId) {  // <- userId를 파라미터로 받음
        PostResponseDto responseDto = postService.createPost(requestDto, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    // 게시글 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> getPost(
            @PathVariable Long id,
            @RequestParam(required = false) Long userId) {
        PostResponseDto responseDto = postService.getPost(id, userId);
        return ResponseEntity.ok(responseDto);
    }

    // 게시글 목록 조회 (전체)
    @GetMapping
    public ResponseEntity<List<PostResponseDto>> getAllPosts(
            @RequestParam(required = false) Long userId) {
        List<PostResponseDto> responseDtos = postService.getAllPosts(userId);
        return ResponseEntity.ok(responseDtos);
    }

    // 게시글 목록 조회 (카테고리별)
    @GetMapping("/category/{category}")
    public ResponseEntity<List<PostResponseDto>> getPostsByCategory(
            @PathVariable String category,
            @RequestParam(required = false) Long userId) {
        List<PostResponseDto> responseDtos = postService.getPostsByCategory(category, userId);
        return ResponseEntity.ok(responseDtos);
    }

    // 게시글 수정
    @PutMapping("/{id}")
    public ResponseEntity<PostResponseDto> updatePost(
            @PathVariable Long id,
            @RequestParam Long userId,
            @RequestBody PostRequestDto requestDto) {
        PostResponseDto responseDto = postService.updatePost(id, userId, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    // 게시글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(
            @PathVariable Long id,
            @RequestParam Long userId) {
        postService.deletePost(id, userId);
        return ResponseEntity.noContent().build();
    }

    // 좋아요 토글
    @PostMapping("/{id}/like")
    public ResponseEntity<PostResponseDto> toggleLike(
            @PathVariable Long id,
            @RequestParam Long userId) {
        PostResponseDto responseDto = postService.toggleLike(id, userId);
        return ResponseEntity.ok(responseDto);
    }
}