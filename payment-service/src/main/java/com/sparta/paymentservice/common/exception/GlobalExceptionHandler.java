package com.sparta.paymentservice.common.exception;

import com.sparta.paymentservice.common.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(PaymentException.class)
    public ResponseEntity<ErrorResponse> handlePaymentException(PaymentException e) {

        return new ResponseEntity<>(
                ErrorResponse.from(e.getErrorCode(), e.getMessage()),
                HttpStatus.BAD_REQUEST);
    }
}
