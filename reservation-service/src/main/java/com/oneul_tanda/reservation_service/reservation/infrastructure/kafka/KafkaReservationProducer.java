package com.oneul_tanda.reservation_service.reservation.infrastructure.kafka;

import com.oneul_tanda.reservation_service.reservation.infrastructure.configuration.ReservationRedisConfig;
import com.oneul_tanda.reservation_service.reservation.infrastructure.kafka.event.ReservationCanceledEvent;
import com.oneul_tanda.reservation_service.reservation.infrastructure.kafka.event.ReservationHeldEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaReservationProducer {

    private final KafkaTemplate<String, ReservationCanceledEvent> kafkaTemplate;
    private final KafkaTemplate<String, ReservationHeldEvent> kafkaTemplateForPending;

    /**
     * 예약 취소 요청 이벤트를 Kafka 토픽에 발행
     */
    public void sendReservationCanceledEvent(UUID reservationId, UUID flightId, int seatCount) {

        ReservationCanceledEvent event = ReservationCanceledEvent.of(
                reservationId,
                flightId,
                seatCount,
                "reservation-canceled");

        kafkaTemplate.send("reservation-canceled", reservationId.toString(), event);
    }

    /**
     * 임시 예약 생성 완료후 상세 입력을 받기위해 알림 서비스에 토픽 발행
     */
    public void sendReservationPendingEvent(UUID flightId, UUID userId, Integer seatCount) {
        ReservationHeldEvent event = ReservationHeldEvent.createReservationEvent(
                flightId,
                userId,
                seatCount,
                "reservation-pending"
                );

        kafkaTemplateForPending.send("reservation-pending", userId.toString(), event);
    }
}
