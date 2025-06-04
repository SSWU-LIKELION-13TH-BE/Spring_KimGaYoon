package com.likelion.Assist_Backend.service;

import com.likelion.Assist_Backend.apiPayload.code.ErrorStatus;
import com.likelion.Assist_Backend.apiPayload.code.SuccessStatus;
import com.likelion.Assist_Backend.apiPayload.dto.ApiResponse;
import com.likelion.Assist_Backend.apiPayload.exception.GeneralException;
import com.likelion.Assist_Backend.dto.UserLoginRequestDto;
import com.likelion.Assist_Backend.dto.UserLoginResponseDto;
import com.likelion.Assist_Backend.dto.UserPasswordChangeRequestDto;
import com.likelion.Assist_Backend.dto.UserSignupRequestDto;
import com.likelion.Assist_Backend.entity.User;
import com.likelion.Assist_Backend.repository.UserRepository;
import com.likelion.Assist_Backend.security.JwtTokenProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public ApiResponse<String> signup(UserSignupRequestDto requestDto) {
        if (userRepository.existsByUserId(requestDto.getUserId())) {
            throw new GeneralException(ErrorStatus.USERNAME_ALREADY_EXISTS);
        }

        User user = new User();
        user.setUserId(requestDto.getUserId());
        user.setPassword(passwordEncoder.encode(requestDto.getPassword ()));
        user.setName(requestDto.getName());
        user.setProfileImage(requestDto.getProfileImage());

        userRepository.save(user);

        return ApiResponse.of(SuccessStatus._OK, "회원가입 성공!");
    }

    public ApiResponse<UserLoginResponseDto> login(UserLoginRequestDto requestDto) {
        User user = userRepository.findByUserId(requestDto.getUserId())
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));
        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new GeneralException(ErrorStatus.PASSWORD_MISMATCH);
        }

        String token = jwtTokenProvider.createToken(user.getUserId());
        UserLoginResponseDto responseDto = new UserLoginResponseDto(user.getUserId(), token);

        return ApiResponse.of(SuccessStatus._OK, responseDto);
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with ID: " + userId));
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUserId())
                .password(user.getPassword())
                .authorities("USER")
                .build();
    }

    public ApiResponse<String> changePassword(String userId, UserPasswordChangeRequestDto dto) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));

        if (!passwordEncoder.matches(dto.getCurrentPassword(), user.getPassword())) {
            throw new GeneralException(ErrorStatus.PASSWORD_MISMATCH);
        }

        if (!dto.getNewPassword().equals(dto.getConfirmPassword())) {
            throw new GeneralException(ErrorStatus.PASSWORD_CONFIRM_MISMATCH);
        }

        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        userRepository.save(user);

        return ApiResponse.of(SuccessStatus._OK, "비밀번호가 성공적으로 변경되었습니다.");
    }


}
