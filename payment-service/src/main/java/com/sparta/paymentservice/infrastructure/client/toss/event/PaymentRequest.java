package com.sparta.paymentservice.infrastructure.client.toss.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {
    private UUID reservationId;
    private UUID userId;
    private Integer totalPrice;
    private String flightInfo;
}
