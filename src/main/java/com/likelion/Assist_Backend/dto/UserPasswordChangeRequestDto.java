package com.likelion.Assist_Backend.dto;

public class UserPasswordChangeRequestDto {
    private String currentPassword;
    private String newPassword;
    private String confirmPassword;

    // 기본 생성자, getter, setter 추가 (또는 Lombok @Data 사용 가능)

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
