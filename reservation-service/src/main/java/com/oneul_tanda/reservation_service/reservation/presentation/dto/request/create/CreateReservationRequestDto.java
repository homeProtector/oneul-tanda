package com.oneul_tanda.reservation_service.reservation.presentation.dto.request.create;

import com.oneul_tanda.reservation_service.passenger.domain.entity.Gender;

import java.util.List;
import java.util.UUID;

public record CreateReservationRequestDto(
        UUID flightId,
        int seatCount,
        List<CreatePassengerDto> passengers
)
{
    public record CreatePassengerDto(
            String name,
            String birth,
            Gender gender,
            String passportNumber
    ) {}
}
