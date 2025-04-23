package com.sparta.paymentservice.web;

import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import com.sparta.paymentservice.application.service.ImPortPaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final ImPortPaymentService imPortPaymentService;

    @PostMapping("/test")
    public ResponseEntity<String> requestTestPayment(@RequestParam UUID reservationId,
                                                     @RequestParam Integer amount) {
        try {
            IamportResponse<Payment> response = imPortPaymentService.requestTestPayment(reservationId, amount);

            if (response.getResponse() != null && "paid".equals(response.getResponse().getStatus())) {
                return ResponseEntity.ok("결제 성공: " + response.getResponse().getApplyNum());
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("결제 실패: " + response.getMessage());
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("결제 요청 중 오류 발생: " + e.getMessage());
        }
    }
}
