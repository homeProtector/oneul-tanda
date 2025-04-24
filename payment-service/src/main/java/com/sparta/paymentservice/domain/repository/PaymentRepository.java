package com.sparta.paymentservice.domain.repository;

import com.sparta.paymentservice.domain.entity.Payments;

public interface PaymentRepository {
    void save(Payments payments);
}
