package com.likelion.Assist_Backend.apiPayload.code;

import com.likelion.Assist_Backend.apiPayload.dto.ErrorReasonDTO;

public interface BaseErrorCode {
    ErrorReasonDTO getReason();
    ErrorReasonDTO getReasonHttpStatus();
}
