package com.sparta.paymentservice.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ImportErrorResponse {
    private int errorCode;
    private String message;

    public static ImportErrorResponse from(int errorCode, String message) {
        return new ImportErrorResponse(errorCode, message);
    }
}
