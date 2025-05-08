package com.oneul_tanda.websocket_service.infrastructure.kafka;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;
@Builder
public record ReservationPendingEvent(
        UUID eventId,                   // 대상: 이벤트 고유 ID or 이벤트 대상 ID
        String eventType,               // 행위: 이벤트 타입    (ex: 예약 선점)
        LocalDateTime reservationTime,  // 시간: 행위 발생 시각  (ex: 선점이 일어난 시간)
        ReservationPendingEvent.Data data                      // 정보: 행위와 관련된 정보
) {
    @Builder
    public record Data (
            UUID flightId,
            UUID userId,
            Integer seatCount
    ){
    }

    public static ReservationPendingEvent of(UUID flightId, UUID userId, String eventType) {
        return ReservationPendingEvent.builder()
                .eventId(UUID.randomUUID())
                .eventType(eventType)
                .reservationTime(LocalDateTime.now())
                .data(ReservationPendingEvent.Data.builder()
                        .flightId(flightId)
                        .userId(userId)
                        .build())
                .build();
    }
}
