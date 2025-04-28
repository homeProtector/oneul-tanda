package com.sparta.paymentservice.common.exception;

import lombok.Getter;

@Getter
public class PaymentException extends RuntimeException {
    private final int errorCode;
    private final String message;

    public PaymentException(int errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }
}