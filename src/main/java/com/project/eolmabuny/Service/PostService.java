package com.project.eolmabuny.Service;

import com.project.eolmabuny.DTO.Post.PostRequestDto;
import com.project.eolmabuny.DTO.PostResponseDto;
import com.project.eolmabuny.Model.Entity.Post;
import com.project.eolmabuny.Model.Entity.PostLike;
import com.project.eolmabuny.Model.Entity.PostLikeId;
import com.project.eolmabuny.Model.Entity.User;
import com.project.eolmabuny.Model.Repository.PostLikeRepository;
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
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostLikeRepository postLikeRepository;

    // 게시글 작성
    @Transactional
    public PostResponseDto createPost(PostRequestDto requestDto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        Post post = Post.builder()
                .user(user)
                .category(requestDto.getCategory())
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .jobTag(requestDto.getJobTag())
                .build();

        Post savedPost = postRepository.save(post);
        return convertToResponseDto(savedPost, userId);
    }

    // 게시글 단건 조회 (조회수 증가)
    @Transactional
    public PostResponseDto getPost(String postUuid, Long userId) {  // Long -> String
        Post post = postRepository.findById(postUuid)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        // 조회수 증가
        post.increaseViewCount();

        return convertToResponseDto(post, userId);
    }

    // 게시글 목록 조회 (전체)
    public List<PostResponseDto> getAllPosts(Long userId) {
        List<Post> posts = postRepository.findAllByOrderByCreatedAtDesc();
        return posts.stream()
                .map(post -> convertToResponseDto(post, userId))
                .collect(Collectors.toList());
    }

    // 게시글 목록 조회 (카테고리별)
    public List<PostResponseDto> getPostsByCategory(String category, Long userId) {
        List<Post> posts = postRepository.findByCategoryOrderByCreatedAtDesc(category);
        return posts.stream()
                .map(post -> convertToResponseDto(post, userId))
                .collect(Collectors.toList());
    }

    // 게시글 수정
    @Transactional
    public PostResponseDto updatePost(String postUuid, Long userId, PostRequestDto requestDto) {  // Long -> String
        Post post = postRepository.findById(postUuid)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        // 작성자 확인
        if (!post.getUser().getUserId().equals(userId)) {
            throw new IllegalArgumentException("게시글 수정 권한이 없습니다.");
        }

        // 수정
        Post updatedPost = Post.builder()
                .postUuid(post.getPostUuid())
                .user(post.getUser())
                .category(requestDto.getCategory() != null ? requestDto.getCategory() : post.getCategory())
                .title(requestDto.getTitle() != null ? requestDto.getTitle() : post.getTitle())
                .content(requestDto.getContent() != null ? requestDto.getContent() : post.getContent())
                .jobTag(requestDto.getJobTag() != null ? requestDto.getJobTag() : post.getJobTag())
                .viewCount(post.getViewCount())
                .comments(post.getComments())
                .postLikes(post.getPostLikes())
                .build();

        Post savedPost = postRepository.save(updatedPost);
        return convertToResponseDto(savedPost, userId);
    }

    // 게시글 삭제
    @Transactional
    public void deletePost(String postUuid, Long userId) {  // Long -> String
        Post post = postRepository.findById(postUuid)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        // 작성자 확인
        if (!post.getUser().getUserId().equals(userId)) {
            throw new IllegalArgumentException("게시글 삭제 권한이 없습니다.");
        }

        postRepository.delete(post);
    }

    // 좋아요 토글 (추가/취소)
    @Transactional
    public PostResponseDto toggleLike(String postUuid, Long userId) {  // Long -> String
        Post post = postRepository.findById(postUuid)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        PostLikeId postLikeId = new PostLikeId(userId, postUuid);  // Long, String

        // 이미 좋아요를 눌렀으면 취소, 안 눌렀으면 추가
        if (postLikeRepository.existsById(postLikeId)) {
            postLikeRepository.deleteById(postLikeId);
        } else {
            PostLike postLike = PostLike.builder()
                    .id(postLikeId)
                    .user(user)
                    .post(post)
                    .build();
            postLikeRepository.save(postLike);
        }

        return convertToResponseDto(post, userId);
    }

    // Entity -> DTO 변환
    private PostResponseDto convertToResponseDto(Post post, Long currentUserId) {
        long likeCount = postLikeRepository.countByPost_PostUuid(post.getPostUuid());
        boolean isLiked = currentUserId != null &&
                postLikeRepository.existsByUser_UserIdAndPost_PostUuid(currentUserId, post.getPostUuid());
        long commentCount = post.getComments().size();

        return PostResponseDto.builder()
                .postUuid(post.getPostUuid())
                .userId(post.getUser().getUserId())
                .nickname(post.getUser().getNickname())
                .category(post.getCategory())
                .title(post.getTitle())
                .content(post.getContent())
                .jobTag(post.getJobTag())
                .viewCount(post.getViewCount())
                .likeCount(likeCount)
                .isLiked(isLiked)
                .commentCount(commentCount)
                .createdAt(post.getCreatedAt())
                .build();
    }
}