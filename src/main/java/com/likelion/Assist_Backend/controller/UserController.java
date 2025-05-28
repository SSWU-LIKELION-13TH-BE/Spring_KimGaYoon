package com.likelion.Assist_Backend.controller;

import com.likelion.Assist_Backend.dto.UserLoginRequestDto;
import com.likelion.Assist_Backend.dto.UserLoginResponseDto;
import com.likelion.Assist_Backend.dto.UserPasswordChangeRequestDto;
import com.likelion.Assist_Backend.dto.UserSignupRequestDto;
import com.likelion.Assist_Backend.entity.User;
import com.likelion.Assist_Backend.repository.UserRepository;
import com.likelion.Assist_Backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/users")

public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository) {

        this.userService = userService;
        this.userRepository = userRepository;
    }

    //회원가입
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody UserSignupRequestDto userDTO) {
        userService.signup(userDTO);
        return ResponseEntity.ok("회원가입 성공!");
    }

    //로그인
    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDto> login(@RequestBody UserLoginRequestDto userDTO) {
        UserLoginResponseDto response = userService.login(userDTO);
        return ResponseEntity.ok(response);
    }

    //사용자 정보 조회 API
    @GetMapping("/me")
    public ResponseEntity<UserDto> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByUserId(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        return ResponseEntity.ok(new UserDto(user.getUserId(), user.getName(), user.getProfileImage()));
    }
    // 사용자 정보 DTO
    public record UserDto(String userId, String name, String profileImage) {}

    //비밀번호 변경 API
    @PostMapping("/password")
    public ResponseEntity<?> changePassword(@RequestBody UserPasswordChangeRequestDto dto, @AuthenticationPrincipal UserDetails userDetails) {
        return userService.changePassword(userDetails.getUsername(), dto);
    }


}
