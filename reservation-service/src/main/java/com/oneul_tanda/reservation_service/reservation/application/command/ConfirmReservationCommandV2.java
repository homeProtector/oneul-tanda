package com.oneul_tanda.reservation_service.reservation.application.command;

import com.oneul_tanda.reservation_service.reservation.application.dto.PassengerDto;
import com.oneul_tanda.reservation_service.reservation.presentation.dto.request.update.ConfirmReservationRequestDtoV2;

import java.util.List;
import java.util.UUID;

public record ConfirmReservationCommandV2(
        UUID userId,
        UUID flightId,
        List<PassengerDto> passengers
) {
    /**
     * 주어진 사용자 ID와 예약 요청 DTO를 기반으로 ConfirmReservationCommandV2 인스턴스를 생성합니다.
     *
     * @param userId 예약을 요청한 사용자의 UUID
     * @param dto 예약 요청 정보를 담은 ConfirmReservationRequestDtoV2 객체
     * @return 생성된 ConfirmReservationCommandV2 인스턴스
     */
    public static ConfirmReservationCommandV2 of(UUID userId, ConfirmReservationRequestDtoV2 dto) {
        return new ConfirmReservationCommandV2(
                userId,
                dto.flightId(),
                dto.passengers() == null ? List.of() : dto.passengers().stream()
                        .map(PassengerDto::from)
                        .toList()
        );
    }
}
