package com.likelion.Assist_Backend.test.controller;

import com.likelion.Assist_Backend.apiPayload.code.SuccessStatus;
import com.likelion.Assist_Backend.apiPayload.dto.ApiResponse;
import com.likelion.Assist_Backend.test.dto.SampleRequestDto;
import com.likelion.Assist_Backend.test.dto.TestResponse;
import com.likelion.Assist_Backend.test.service.TestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;

    @GetMapping("/hello")
    public ApiResponse<TestResponse> hello(){
        return ApiResponse.of(SuccessStatus._OK, new TestResponse("Hello, API!"));
    }

    @GetMapping("/error")
    public ApiResponse<TestResponse> error(@RequestParam(required = false) Integer flag){
        testService.checkFlag(flag);
        return ApiResponse.of(SuccessStatus._OK, new TestResponse("정상 처리되었습니다."));
    }

    @PostMapping("/validate")
    public ApiResponse<String> validate(@Valid @RequestBody SampleRequestDto dto){
        return ApiResponse.of(SuccessStatus._OK, "통과되었습니다");
    }

}
