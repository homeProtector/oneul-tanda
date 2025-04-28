package com.sparta.paymentservice.common.exception;

import lombok.Getter;

@Getter
public class ImportException extends RuntimeException {
    private final int errorCode;
    private final String message;

    public ImportException(int errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }
}