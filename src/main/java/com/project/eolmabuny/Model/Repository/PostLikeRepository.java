package com.project.eolmabuny.Model.Repository;

import com.project.eolmabuny.Model.Entity.PostLike;
import com.project.eolmabuny.Model.Entity.PostLikeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, PostLikeId> {
    // 특정 게시글의 좋아요 개수
    long countByPost_PostId(Long postId);

    // 특정 사용자가 특정 게시글에 좋아요를 눌렀는지 확인
    boolean existsByUser_UserIdAndPost_PostId(Long userId, Long postId);
}