package com.sparta.paymentservice.infrastructure.client;

import com.sparta.paymentservice.infrastructure.client.toss.event.PaymentKeyInRequest;
import com.sparta.paymentservice.infrastructure.client.toss.event.PaymentRequest;
import com.sparta.paymentservice.infrastructure.client.toss.event.PaymentResponse;
import com.sparta.paymentservice.infrastructure.client.toss.event.TossKeyInResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "tossPaymentClient", url = "${toss.base-url}")
public interface TossPaymentsClient {
    @PostMapping("/payments/key-in")
    TossKeyInResponse requestKeyInPayment(@RequestHeader("Authorization") String authorization,
                                          @RequestBody PaymentKeyInRequest request);

    @PostMapping(value = "/v1/payments/confirm", consumes = "application/json")
    PaymentResponse requestPayment(@RequestHeader("Authorization") String authorization,
                                   @RequestBody PaymentRequest request);
}
