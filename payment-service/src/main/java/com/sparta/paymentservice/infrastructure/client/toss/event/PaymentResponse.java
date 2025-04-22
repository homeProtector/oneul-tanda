package com.sparta.paymentservice.infrastructure.client.toss.event;

import com.sparta.paymentservice.domain.entity.PaymentStatusEnum;
import com.sparta.paymentservice.domain.entity.PaymentTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponse {
    private UUID reservationId;
    private UUID userId;
    private Integer totalPrice;
    private String paymentKey;
    private PaymentStatusEnum status;
    private PaymentTypeEnum paymentType;
}
