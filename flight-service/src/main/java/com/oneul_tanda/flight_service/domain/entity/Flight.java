package com.oneul_tanda.flight_service.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "m_flights")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "flight_num", nullable = false, unique = true)
    private String flightNum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "airline_id", nullable = false)
    private AirLine airline;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departure_airport_id", nullable = false)
    private Airport departureAirportCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "arrival_airport_id", nullable = false)
    private Airport arrivalAirportCode;

    @Column(name = "departure_date", nullable = false)
    private LocalDateTime departureDate;

    @Column(name = "arrival_date", nullable = false)
    private LocalDateTime arrivalDate;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "remaining_seats", nullable = false)
    private int remainingSeats;

    public static Flight from(String flightNum, AirLine airline, Airport departureAirportCode,
                              Airport arrivalAirportCode,
                              LocalDateTime departureDate, LocalDateTime arrivalDate, BigDecimal price,
                              int remainingSeats
    ) {
        return Flight.builder()
                .flightNum(flightNum)
                .airline(airline)
                .departureAirportCode(departureAirportCode)
                .arrivalAirportCode(arrivalAirportCode)
                .departureDate(departureDate)
                .arrivalDate(arrivalDate)
                .price(price)
                .remainingSeats(remainingSeats)
                .build();

    }

    public void updateOf(String flightNum, AirLine airline, Airport departureAirportCode, Airport arrivalAirportCode,
                         LocalDateTime departureDate, LocalDateTime arrivalDate, BigDecimal price, int remainingSeats
    ) {
        this.flightNum = flightNum;
        this.airline = airline;
        this.departureAirportCode = departureAirportCode;
        this.arrivalAirportCode = arrivalAirportCode;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.price = price;
        this.remainingSeats = remainingSeats;
    }
}
