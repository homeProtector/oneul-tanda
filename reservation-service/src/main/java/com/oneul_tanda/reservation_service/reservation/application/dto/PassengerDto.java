package com.oneul_tanda.reservation_service.reservation.application.dto;

import com.oneul_tanda.reservation_service.passenger.domain.entity.Gender;
import com.oneul_tanda.reservation_service.reservation.presentation.dto.request.update.ConfirmReservationRequestDtoV2;

public record PassengerDto(
        String name,
        String birth,
        Gender gender,
        String passportNumber
) {
    /**
     * ConfirmPassengerDtoV2 객체를 PassengerDto로 변환합니다.
     *
     * @param dto 변환할 ConfirmPassengerDtoV2 객체
     * @return 변환된 PassengerDto 인스턴스
     */
    public static PassengerDto from(ConfirmReservationRequestDtoV2.ConfirmPassengerDtoV2 dto) {
        return new PassengerDto(
                dto.name(),
                dto.birth(),
                dto.gender(),
                dto.passportNumber()
        );
    }
}
