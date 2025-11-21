package com.project.eolmabuny.Model.Repository;

import com.project.eolmabuny.Model.Entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    // 특정 게시글의 댓글 조회 (최상위 댓글만)
    List<Comment> findByPost_PostUuidAndParentCommentIsNullOrderByCreatedAtAsc(String postUuid);  // PostId -> PostUuid

    // 특정 댓글의 대댓글 조회
    List<Comment> findByParentComment_CommentIdOrderByCreatedAtAsc(Long parentCommentId);

    // 특정 사용자의 댓글인지 확인
    Optional<Comment> findByCommentIdAndUser_UserId(Long commentId, Long userId);
}