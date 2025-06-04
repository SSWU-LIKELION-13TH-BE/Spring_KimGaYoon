package com.likelion.Assist_Backend.apiPayload.code;

import com.likelion.Assist_Backend.apiPayload.dto.ErrorReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode{
    _BAD_REQUEST(HttpStatus.BAD_REQUEST,"COMMON400","잘못된 요청입니다."),
    TEMP_EXCEPTION(HttpStatus.BAD_REQUEST, "TEMP4001", "테스트용 예외입니다."),

    USERNAME_ALREADY_EXISTS(HttpStatus.CONFLICT, "USER409", "이미 존재하는 아이디입니다."), // 회원가입 중복 검사
    PASSWORD_MISMATCH(HttpStatus.BAD_REQUEST, "USER4001", "현재 비밀번호가 일치하지 않습니다."), // 비밀번호 변경 - 현재 비밀번호 불일치
    PASSWORD_CONFIRM_MISMATCH(HttpStatus.BAD_REQUEST, "USER4002", "새 비밀번호와 확인 비밀번호가 일치하지 않습니다."), // 비밀번호 변경 - 새 비번 불일치
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER404", "사용자를 찾을 수 없습니다."); // 사용자 조회 실패

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ErrorReasonDTO getReason() {
        return ErrorReasonDTO.builder().message(message).code(code).isSuccess(false).build();
    }

    @Override
    public ErrorReasonDTO getReasonHttpStatus() {
        return ErrorReasonDTO.builder().message(message).code(code).isSuccess(false).httpStatus(httpStatus).build();
    }
}
