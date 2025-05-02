package com.oneul_tanda.reservation_service.reservation.infrastructure.kafka;

import com.oneul_tanda.reservation_service.reservation.application.command.CreateHoldReservationCommand;
import com.oneul_tanda.reservation_service.reservation.application.service.ReservationService;
import com.oneul_tanda.reservation_service.reservation.infrastructure.kafka.event.ReservationHeldEvent;
import com.oneul_tanda.reservation_service.reservation.presentation.dto.response.create.CreateHoldReservationResponseDto;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ReservationHeldEventConsumer {

    private final ReservationService reservationService;


    /**
     * Kafka에서 예약 보류 이벤트를 수신하여 임시 예약을 생성합니다.
     *
     * 예약 보류 이벤트를 받아 해당 항공편, 사용자, 좌석 정보를 추출한 뒤 임시 예약을 생성합니다.
     * 항공편 조회 실패 시 에러를 기록하며, 기타 예외 발생 시에도 에러를 기록합니다.
     *
     * @param event 예약 보류 이벤트 데이터
     */
    @KafkaListener(
            topics = "reservation-held",
            groupId = "reservation-service",
            containerFactory = "reservationHeldListenerFactory"
    )
    public void consumeReservationHeldForReservation(ReservationHeldEvent event) {

        try{
            var data = event.data();

            CreateHoldReservationCommand command = CreateHoldReservationCommand.of(
                    data.flightId(),
                    data.userId(),
                    data.seatCount()
            );

            // 예약 임시 생성(사용자가 추후 예약정보 입력하여 예약 완료)
            reservationService.createHoldReservationV2(command);

        } catch (FeignException e) {
            log.error("항공편 조회 실패 - flightId={}, error={}", event.data().flightId(), e.getMessage());
            // 좌석 복원 보상 이벤트 발행? (예: DLQ 또는 Kafka 재시도)

        } catch (Exception e) {
            log.error("임시 예약 생성 실패: {}, {}", event, e.getMessage(), e);
            // -> 여기서도 DLQ 보내거나 알림, 재시도 큐 등록 등 처리 가능
        }


    }

}
