package com.likelion.Assist_Backend.controller;

import com.likelion.Assist_Backend.dto.UserLoginRequestDto;
import com.likelion.Assist_Backend.dto.UserLoginResponseDto;
import com.likelion.Assist_Backend.dto.UserSignupRequestDto;
import com.likelion.Assist_Backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")

public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
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
}
