package com.project.eolmabuny.Controller;

import com.project.eolmabuny.DTO.JobRequestDto;
import com.project.eolmabuny.DTO.JobResponseDto;
import com.project.eolmabuny.Service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;

    // 알바 등록
    @PostMapping
    public ResponseEntity<JobResponseDto> createJob(@RequestBody JobRequestDto requestDto) {
        JobResponseDto responseDto = jobService.createJob(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    // 내 알바 목록 조회
    @GetMapping("/my")
    public ResponseEntity<List<JobResponseDto>> getMyJobs(@RequestParam Long userId) {
        List<JobResponseDto> responseDtos = jobService.getJobsByUserId(userId);
        return ResponseEntity.ok(responseDtos);
    }

    // 내 알바 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<JobResponseDto> getJob(
            @PathVariable Long id,
            @RequestParam Long userId) {
        JobResponseDto responseDto = jobService.getJob(id, userId);
        return ResponseEntity.ok(responseDto);
    }

    // 알바 수정
    @PutMapping("/{id}")
    public ResponseEntity<JobResponseDto> updateJob(
            @PathVariable Long id,
            @RequestParam Long userId,
            @RequestBody JobRequestDto requestDto) {
        JobResponseDto responseDto = jobService.updateJob(id, userId, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    // 알바 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJob(
            @PathVariable Long id,
            @RequestParam Long userId) {
        jobService.deleteJob(id, userId);
        return ResponseEntity.noContent().build();
    }
}