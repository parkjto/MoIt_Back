package com.project.eolmabuny.Service;

import com.project.eolmabuny.DTO.JobRequestDto;
import com.project.eolmabuny.DTO.JobResponseDto;
import com.project.eolmabuny.Model.Entity.Job;
import com.project.eolmabuny.Model.Entity.User;
import com.project.eolmabuny.Model.Repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JobService {

    private final JobRepository jobRepository;

    // 알바 생성
    @Transactional
    public JobResponseDto createJob(JobRequestDto requestDto) {
        User user = User.builder()
                .userId(requestDto.getUserId())
                .build();

        Job job = Job.builder()
                .user(user)
                .jobName(requestDto.getJobName())
                .hourlyWage(requestDto.getHourlyWage())
                .workplaceName(requestDto.getWorkplaceName())
                .build();

        Job savedJob = jobRepository.save(job);
        return convertToResponseDto(savedJob);
    }

    // 특정 사용자의 알바 목록 조회
    public List<JobResponseDto> getJobsByUserId(Long userId) {
        List<Job> jobs = jobRepository.findByUser_UserId(userId);
        return jobs.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    // 특정 사용자의 특정 알바 조회 (권한 확인 포함)
    public JobResponseDto getJob(Long jobId, Long userId) {
        Job job = jobRepository.findByJobIdAndUser_UserId(jobId, userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 알바가 존재하지 않거나 접근 권한이 없습니다."));
        return convertToResponseDto(job);
    }

    // 알바 수정 (권한 확인 포함)
    @Transactional
    public JobResponseDto updateJob(Long jobId, Long userId, JobRequestDto requestDto) {
        Job job = jobRepository.findByJobIdAndUser_UserId(jobId, userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 알바가 존재하지 않거나 접근 권한이 없습니다."));

        Job updatedJob = Job.builder()
                .jobId(job.getJobId())
                .user(job.getUser())
                .jobName(requestDto.getJobName())
                .hourlyWage(requestDto.getHourlyWage())
                .workplaceName(requestDto.getWorkplaceName())
                .createdAt(job.getCreatedAt())
                .build();

        Job savedJob = jobRepository.save(updatedJob);
        return convertToResponseDto(savedJob);
    }

    // 알바 삭제 (권한 확인 포함)
    @Transactional
    public void deleteJob(Long jobId, Long userId) {
        Job job = jobRepository.findByJobIdAndUser_UserId(jobId, userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 알바가 존재하지 않거나 접근 권한이 없습니다."));
        jobRepository.delete(job);
    }

    // Entity -> DTO 변환
    private JobResponseDto convertToResponseDto(Job job) {
        return JobResponseDto.builder()
                .jobId(job.getJobId())
                .userId(job.getUser().getUserId())
                .jobName(job.getJobName())
                .hourlyWage(job.getHourlyWage())
                .workplaceName(job.getWorkplaceName())
                .createdAt(job.getCreatedAt())
                .build();
    }
}