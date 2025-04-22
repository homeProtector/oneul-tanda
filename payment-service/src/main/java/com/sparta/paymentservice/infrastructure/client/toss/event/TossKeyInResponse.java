package com.sparta.paymentservice.infrastructure.client.toss.event;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TossKeyInResponse {
    private String paymentKey;
    private String orderId;
    private String status;
    private String requestedAt;
}
