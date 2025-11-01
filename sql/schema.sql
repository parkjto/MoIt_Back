-- 기존 데이터베이스 완전 삭제 후 재생성
DROP DATABASE IF EXISTS eolmabunny;
CREATE DATABASE eolmabunny CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE eolmabunny;

-- User 테이블
CREATE TABLE user (
    user_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    nickname VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Job 테이블 (is_active 제거)
CREATE TABLE job (
    job_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    job_name VARCHAR(100) NOT NULL,
    hourly_wage INT NOT NULL,
    workplace_name VARCHAR(100),    
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user(user_id) ON DELETE CASCADE
);

-- WorkSchedule 테이블 (status, actual_hours 제거)
CREATE TABLE work_schedule (
    schedule_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    job_id BIGINT NOT NULL,
    start_time DATETIME NOT NULL,
    end_time DATETIME NOT NULL,
    memo TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (job_id) REFERENCES job(job_id) ON DELETE CASCADE
);

-- SavingPlan 테이블
CREATE TABLE saving_plan (
    plan_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    plan_name VARCHAR(100) NOT NULL,
    target_amount DECIMAL(15,2) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user(user_id) ON DELETE CASCADE
);

-- Post 테이블 (is_deleted 제거)
CREATE TABLE post (
    post_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    category VARCHAR(50),
    title VARCHAR(200) NOT NULL,
    content TEXT NOT NULL,
    job_tag VARCHAR(50),
    view_count INT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user(user_id) ON DELETE CASCADE
);

-- Comment 테이블
CREATE TABLE comment (
    comment_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    post_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    parent_comment_id BIGINT,
    content TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (post_id) REFERENCES post(post_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES user(user_id) ON DELETE CASCADE,
    FOREIGN KEY (parent_comment_id) REFERENCES comment(comment_id) ON DELETE CASCADE
);

-- PostLike 테이블
CREATE TABLE post_like (
    user_id BIGINT NOT NULL,
    post_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id, post_id),
    FOREIGN KEY (user_id) REFERENCES user(user_id) ON DELETE CASCADE,
    FOREIGN KEY (post_id) REFERENCES post(post_id) ON DELETE CASCADE
);

-- 인덱스 추가
CREATE INDEX idx_job_user ON job(user_id);
CREATE INDEX idx_schedule_job ON work_schedule(job_id);
CREATE INDEX idx_post_user ON post(user_id);
CREATE INDEX idx_comment_post ON comment(post_id);
CREATE INDEX idx_comment_parent ON comment(parent_comment_id);
