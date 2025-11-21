package com.project.eolmabuny.Controller;

import com.project.eolmabuny.DTO.Post.PostRequestDto;
import com.project.eolmabuny.DTO.PostResponseDto;
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
            @RequestParam Long userId) {
        PostResponseDto responseDto = postService.createPost(requestDto, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    // 게시글 단건 조회 - UUID 사용!
    @GetMapping("/{uuid}")  // {id} -> {uuid}
    public ResponseEntity<PostResponseDto> getPost(
            @PathVariable String uuid,  // Long id -> String uuid
            @RequestParam(required = false) Long userId) {
        PostResponseDto responseDto = postService.getPost(uuid, userId);
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

    // 게시글 수정 - UUID 사용!
    @PutMapping("/{uuid}")  // {id} -> {uuid}
    public ResponseEntity<PostResponseDto> updatePost(
            @PathVariable String uuid,  // Long id -> String uuid
            @RequestParam Long userId,
            @RequestBody PostRequestDto requestDto) {
        PostResponseDto responseDto = postService.updatePost(uuid, userId, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    // 게시글 삭제 - UUID 사용!
    @DeleteMapping("/{uuid}")  // {id} -> {uuid}
    public ResponseEntity<Void> deletePost(
            @PathVariable String uuid,  // Long id -> String uuid
            @RequestParam Long userId) {
        postService.deletePost(uuid, userId);
        return ResponseEntity.noContent().build();
    }

    // 좋아요 토글 - UUID 사용!
    @PostMapping("/{uuid}/like")  // {id} -> {uuid}
    public ResponseEntity<PostResponseDto> toggleLike(
            @PathVariable String uuid,  // Long id -> String uuid
            @RequestParam Long userId) {
        PostResponseDto responseDto = postService.toggleLike(uuid, userId);
        return ResponseEntity.ok(responseDto);
    }
}