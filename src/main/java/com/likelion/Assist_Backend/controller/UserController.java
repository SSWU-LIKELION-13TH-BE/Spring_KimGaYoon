package com.likelion.Assist_Backend.controller;

import com.likelion.Assist_Backend.apiPayload.dto.ApiResponse;
import com.likelion.Assist_Backend.dto.UserLoginRequestDto;
import com.likelion.Assist_Backend.dto.UserLoginResponseDto;
import com.likelion.Assist_Backend.dto.UserPasswordChangeRequestDto;
import com.likelion.Assist_Backend.dto.UserSignupRequestDto;
import com.likelion.Assist_Backend.entity.User;
import com.likelion.Assist_Backend.repository.UserRepository;
import com.likelion.Assist_Backend.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/users")
//@RequiredArgsConstructor

public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository) {

        this.userService = userService;
        this.userRepository = userRepository;
    }

    //회원가입
    @PostMapping("/signup")
    public ApiResponse<String> signup(@Valid @RequestBody UserSignupRequestDto userDTO) {
        return userService.signup(userDTO);
    }

    //로그인
    @PostMapping("/login")
    public ApiResponse<UserLoginResponseDto> login(@RequestBody UserLoginRequestDto userDTO) {
        return userService.login(userDTO);
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
    public ApiResponse<String> changePassword(@Valid @RequestBody UserPasswordChangeRequestDto dto,
                                              @AuthenticationPrincipal UserDetails userDetails) {
        return userService.changePassword(userDetails.getUsername(), dto);
    }


}
