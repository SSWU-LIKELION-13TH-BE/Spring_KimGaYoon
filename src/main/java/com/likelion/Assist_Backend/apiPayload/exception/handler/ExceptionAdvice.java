package com.likelion.Assist_Backend.apiPayload.exception.handler;

import com.likelion.Assist_Backend.apiPayload.code.ErrorStatus;
import com.likelion.Assist_Backend.apiPayload.dto.ApiResponse;
import com.likelion.Assist_Backend.apiPayload.dto.ErrorReasonDTO;
import com.likelion.Assist_Backend.apiPayload.exception.GeneralException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class ExceptionAdvice extends ResponseEntityExceptionHandler {
    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<ApiResponse<Object>> handleCustomException (GeneralException e){
        ErrorReasonDTO reason = e.getErrorReasonHttpStatus();
        return new ResponseEntity<>(ApiResponse.onFailure(reason.getCode (), reason.getMessage(), null), reason.getHttpStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e,
                                                                  HttpHeaders headers, HttpStatusCode status, WebRequest request){
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(err -> errors.put(err.getField(), err.getDefaultMessage()));
        return new ResponseEntity<>(ApiResponse.onFailure(ErrorStatus._BAD_REQUEST.getCode(), "Validation Error", errors),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleException(Exception e) {
        e.printStackTrace();
        return new ResponseEntity<>(ApiResponse.onFailure(ErrorStatus._BAD_REQUEST.getCode(), e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
