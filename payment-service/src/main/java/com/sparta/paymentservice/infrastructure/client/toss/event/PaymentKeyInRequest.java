package com.sparta.paymentservice.infrastructure.client.toss.event;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentKeyInRequest {
    private Integer amount;
    private String orderId;
    private String orderName;
}
