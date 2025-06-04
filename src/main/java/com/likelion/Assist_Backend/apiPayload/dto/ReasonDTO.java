package com.likelion.Assist_Backend.apiPayload.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
@Getter
@Builder
public class ReasonDTO {
    private final boolean isSuccess;
    private final String code;
    private final String message;
    private HttpStatus httpStatus;
}
