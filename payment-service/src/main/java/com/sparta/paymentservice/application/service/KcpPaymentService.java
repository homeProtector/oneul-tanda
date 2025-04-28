package com.sparta.paymentservice.application.service;


import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.request.CancelData;
import com.siot.IamportRestClient.request.CardInfo;
import com.siot.IamportRestClient.request.OnetimePaymentData;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import com.sparta.paymentservice.application.dto.PaymentRequestDto;
import com.sparta.paymentservice.application.dto.PaymentResponseDto;
import com.sparta.paymentservice.common.exception.ErrorCode;
import com.sparta.paymentservice.common.exception.IamPortException;
import com.sparta.paymentservice.common.exception.PaymentException;
import com.sparta.paymentservice.domain.entity.Payments;
import com.sparta.paymentservice.domain.repository.PaymentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class KcpPaymentService implements PaymentService {
    private final IamportClient iamportClient;
    private final PaymentRepository paymentRepository;

    @Override
    public PaymentResponseDto confirmPayment(PaymentRequestDto request, CardInfo card) {
        try {
            // 테스트용 결제 정보 설정
            UUID reservationId = request.getReservationId();
            String merchantUid = UUID.randomUUID().toString();
            BigDecimal totalPrice = request.getTotalPrice();

            existPayment(reservationId);

            OnetimePaymentData onetimePaymentData = new OnetimePaymentData(merchantUid, totalPrice, card);
            onetimePaymentData.setPg("kcp");
            // 결제 요청
            IamportResponse<Payment> response = iamportClient.onetimePayment(onetimePaymentData);

            if (response.getCode() != 0 || response.getResponse() == null) {
                throw new IamPortException(response.getCode(), response.getMessage());
            }

            Payment payment = response.getResponse();
            PaymentResponseDto responseDto = PaymentResponseDto.toDto(payment, reservationId);
            Payments payments = Payments.create(
                    reservationId,
                    merchantUid,
                    totalPrice,
                    responseDto.getStatus());
            // 결제 기록 저장
            paymentRepository.save(payments);

            return responseDto;

        } catch (IamportResponseException e) {
            throw new IamPortException(e.getHttpStatusCode(), "결제 응답 오류: " +  e.getMessage());
        } catch (IOException e) {
            throw new PaymentException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    public PaymentResponseDto cancelPayment(UUID reservationId) {
        try {
            List<Payments> payments = paymentRepository.findAllByReservationId(reservationId);

            if (payments.isEmpty()) {
                throw new PaymentException(ErrorCode.PAYMENT_NOT_FOUND);
            }
            Payments paidPayment = null;
            for (Payments payment : payments) {
                if ("paid".equals(payment.getStatus())) {
                    paidPayment = payment;
                    break;
                }
            }

            if (paidPayment == null) {
                throw new PaymentException(ErrorCode.PAYMENT_NOT_FOUND_PAID);
            }


            String merchantUid = paidPayment.getMerchantId();

            CancelData cancelData = new CancelData(merchantUid, false);
            cancelData.setReason("사용자 요청에 의한 취소");
            IamportResponse<Payment> response = iamportClient.cancelPaymentByImpUid(cancelData);
            Payment payment = response.getResponse();
            PaymentResponseDto responseDto = PaymentResponseDto.toDto(payment, reservationId);

            paidPayment.updateStatus(responseDto.getStatus());

            return  responseDto;
        }  catch (IamportResponseException e) {
            throw new IamPortException(e.getHttpStatusCode(), "결제 응답 오류: " +  e.getMessage());
        } catch (IOException e) {
            throw new PaymentException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    private void existPayment(UUID reservationId) {
        Payments payments = paymentRepository.findByReservationId(reservationId);
        if (payments == null) {
            return;
        }
        if (payments.getStatus().equals("paid")) {
            throw new PaymentException(ErrorCode.PAYMENT_CONFLICT);
        }
    }
}
