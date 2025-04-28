package com.sparta.paymentservice.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private int errorCode;
    private String message;

    public static ErrorResponse from(int errorCode, String message) {
        return new ErrorResponse(errorCode, message);
    }
}
