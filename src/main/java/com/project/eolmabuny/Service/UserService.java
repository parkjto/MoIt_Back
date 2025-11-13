package com.project.eolmabuny.Service;

import com.project.eolmabuny.DTO.UserRequestDto;
import com.project.eolmabuny.DTO.UserResponseDto;
import com.project.eolmabuny.Model.Entity.User;
import com.project.eolmabuny.Model.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    //회원가입
    @Transactional
    public UserResponseDto createUser(UserRequestDto requestDto) {
        User user = User.builder()
                .userId(requestDto.getUserId())
                .email(requestDto.getEmail())
                .nickname(requestDto.getNickname())
                .password(requestDto.getPassword())
                .build();

        return convertToResponseDto(userRepository.save(user));
    }

    //특정 유저 조회
    public UserResponseDto getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        return convertToResponseDto(user);
    }

    //현재 유저 정보
    public UserResponseDto getCurrentUser() {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(currentUsername)
                .orElseThrow(() -> new IllegalArgumentException("현재 로그인한 사용자를 찾을 수 없습니다."));
        return convertToResponseDto(user);
    }

    //내 정보 수정
    @Transactional
    public UserResponseDto updateUser(Long id, UserRequestDto requestDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        user.setEmail(requestDto.getEmail());
        user.setNickname(requestDto.getNickname());
        if (requestDto.getPassword() != null && !requestDto.getPassword().isEmpty()) {
            user.setPassword(requestDto.getPassword());
        }

        return convertToResponseDto(user);
    }

    //회원 탈퇴
    @Transactional
    public void deleteUser(Long id) {
        userRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        userRepository.deleteById(id);
    }

    //로그인
    //public LoginResponseDto login(LoginRequestDto requestDto) {
    //        Optional<User> optionalUser = userRepository.findByEmail(requestDto.getEmail());
    //        if (optionalUser.isEmpty()) {
    //            throw new IllegalArgumentException("이메일 또는 비밀번호가 잘못되었습니다.");
    //        }
    //
    //        User user = optionalUser.get();
    //        if (!user.getPassword().equals(requestDto.getPassword())) {
    //            throw new IllegalArgumentException("이메일 또는 비밀번호가 잘못되었습니다.");
    //        }
    //
    //        // 실제로는 JWT 토큰 발급 로직이 들어감
    //        String fakeToken = "Bearer faketoken-" + user.getId();
    //
    //        return LoginResponseDto.builder()
    //                .token(fakeToken)
    //                .userId(user.getId())
    //                .email(user.getEmail())
    //                .nickname(user.getNickname())
    //                .build();
    //    }

    //로그아웃
    //public void logout(String token) {
    //        // 실제 구현에서는 토큰 블랙리스트 추가 등 수행
    //        System.out.println("Logout token: " + token);
    //    }

    //dto 변환
    private UserResponseDto convertToResponseDto(User user) {
        return UserResponseDto.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .createdAt(user.getCreatedAt())
                .build();
    }
}