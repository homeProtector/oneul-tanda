package com.oneul_tanda.flight_service.presentation.dtos.flight;

import com.oneul_tanda.flight_service.domain.entity.FlightEntity;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
public class FlightResponse {

    private UUID id;
    private String flightNum;
    private String airlineCode;
    private String departureAirportCode;
    private String arrivalAirportCode;
    private LocalDateTime departureDate;
    private LocalDateTime arrivalDate;
    private BigDecimal price;
    private int remainingSeats;

    public static FlightResponse from(
            FlightEntity flight
    ) {
        return FlightResponse.builder()
                .id(flight.getId())
                .flightNum(flight.getFlightNum())
                .airlineCode(flight.getAirline().getCode())
                .departureAirportCode(flight.getDepartureAirport().getCode())
                .arrivalAirportCode(flight.getArrivalAirport().getCode())
                .departureDate(flight.getDepartureDate())
                .arrivalDate(flight.getArrivalDate())
                .price(flight.getPrice())
                .remainingSeats(flight.getRemainingSeats())
                .build();
    }
}
