package com.likelion.Assist_Backend.apiPayload.code;

import com.likelion.Assist_Backend.apiPayload.dto.ReasonDTO;

public interface BaseCode {
    ReasonDTO  getReason();
    ReasonDTO getReasonHttpStatus();
}
