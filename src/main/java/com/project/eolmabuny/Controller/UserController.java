package com.project.eolmabuny.Controller;

import com.project.eolmabuny.DTO.LoginRequestDto;
import com.project.eolmabuny.DTO.UserRequestDto;
import com.project.eolmabuny.DTO.UserResponseDto;
import com.project.eolmabuny.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    //회원가입
    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserRequestDto requestDto){
        UserResponseDto responseDto = userService.createUser(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    //특정 유저 조회
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
        UserResponseDto user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    //내 정보 조회
    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getMyInfo() {
        // SecurityContext에서 현재 로그인한 유저 정보 가져오기
        UserResponseDto user = userService.getCurrentUser();
        return ResponseEntity.ok(user);
    }

    //내 정보 수정
    @PutMapping("/me")
    public ResponseEntity<UserResponseDto> updateUser(
            @PathVariable Long id,
            @RequestBody UserRequestDto requestDto) {
        UserResponseDto currentUser = userService.getCurrentUser();
        UserResponseDto updatedUser = userService.updateUser(currentUser.getUserId(), requestDto);
        return ResponseEntity.ok(updatedUser);
    }

    //회원 탈퇴
    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        UserResponseDto currentUser = userService.getCurrentUser();
        userService.deleteUser(currentUser.getUserId());
        return ResponseEntity.noContent().build();
    }

    //로그인
    //@PostMapping("/login")
    //public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto requestDto) {
    //    LoginResponseDto response = userService.login(requestDto);
    //    return ResponseEntity.ok(response);
    //}

    //로그아웃
    //@PostMapping("/logout")
    //public ResponseEntity<Void> logout(@RequestHeader("Authorization") String token) {
    //    userService.logout(token);
    //    return ResponseEntity.ok().build();
    //}

    //내 정보 조회 (토큰 기반)
    //@GetMapping("/me/token")
    //public ResponseEntity<UserResponseDto> getMyInfo(@RequestHeader("Authorization") String token) {
    //    UserResponseDto user = userService.getMyInfo(token);
    //    return ResponseEntity.ok(user);
    //}
}