package com.likelion.Assist_Backend.test.service;

import com.likelion.Assist_Backend.apiPayload.code.ErrorStatus;
import com.likelion.Assist_Backend.apiPayload.exception.GeneralException;
import org.springframework.stereotype.Service;

@Service
public class TestService {
    public void checkFlag(Integer flag){
        if (flag != null && flag == 1){
            throw new GeneralException(ErrorStatus.TEMP_EXCEPTION);
        }
    }
}

