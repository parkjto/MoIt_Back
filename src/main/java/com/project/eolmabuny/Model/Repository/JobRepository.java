package com.project.eolmabuny.Model.Repository;

import com.project.eolmabuny.Model.Entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    // 특정 사용자의 알바 목록 조회
    List<Job> findByUser_UserId(Long userId);

    // 특정 사용자의 특정 알바 조회 (권한 확인용)
    Optional<Job> findByJobIdAndUser_UserId(Long jobId, Long userId);
}