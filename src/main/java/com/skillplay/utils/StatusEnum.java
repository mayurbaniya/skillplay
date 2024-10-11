package com.skillplay.utils;

public enum StatusEnum {

    ACTIVE("ACTIVE"),
    ONGOING("ONGOING"),
    CANCELLED("CANCELLED"),
    FINISHED("FINISHED"),
    BANNED("BANNED"),
    DELETED("DELETED"),
    ADMIN("ADMIN"),
    SUCCESS("SUCCESS"),
    FAILED("FAILED"),
    INVALID_PASSWORD("INVALID_PASSWORD"),
    USER_NOT_FOUND("USER_NOT_FOUND"),
    ERROR("ERROR"),
    OTP_EXPIRED("OTP_EXPIRED"),
    INVALID_CLIENT_ID("INVALID_CLIENT_ID"),
    INVALID_OTP("INVALID_OTP");

    private final String status;

     StatusEnum(String status) {
        this.status = status;
     }

    public String getCode() {
        return status;
    }

    @Override
    public String toString() {
        return this.status;
    }

    public String get(){
        return this.status;
    }

}
