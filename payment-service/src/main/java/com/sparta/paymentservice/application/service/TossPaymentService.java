package com.sparta.paymentservice.application.service;

import com.sparta.paymentservice.infrastructure.client.TossPaymentsClient;
import com.sparta.paymentservice.infrastructure.client.toss.event.PaymentKeyInRequest;
import com.sparta.paymentservice.infrastructure.client.toss.event.PaymentRequest;
import com.sparta.paymentservice.infrastructure.client.toss.event.PaymentResponse;
import com.sparta.paymentservice.infrastructure.client.toss.event.TossKeyInResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TossPaymentService implements PaymentService {
    private final TossPaymentsClient tossPaymentsClient;

    @Value("${toss.secret-key}")
    private String secretKey;
    private final String authorizationHeader = "Bearer " + secretKey;

    public PaymentResponse confirmPayment(PaymentRequest request){


        return tossPaymentsClient.requestPayment(authorizationHeader, request);
    }

    public TossKeyInResponse createPayment(PaymentKeyInRequest request){

        return tossPaymentsClient.requestKeyInPayment(authorizationHeader, request);
    }
}
