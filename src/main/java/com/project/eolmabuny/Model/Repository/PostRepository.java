package com.project.eolmabuny.Model.Repository;

import com.project.eolmabuny.Model.Entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, String> {  // Long -> String으로 변경!

    // 카테고리별 게시글 조회
    List<Post> findByCategoryOrderByCreatedAtDesc(String category);

    // 전체 게시글 조회 (최신순)
    List<Post> findAllByOrderByCreatedAtDesc();

    // 특정 사용자의 게시글 조회
    List<Post> findByUser_UserIdOrderByCreatedAtDesc(Long userId);

    // 제목 검색
    List<Post> findByTitleContainingOrderByCreatedAtDesc(String keyword);
}