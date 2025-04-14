package com.oneul_tanda.flight_service.application.service.flight;

import com.oneul_tanda.flight_service.application.dtos.flight.CreateFlightCommand;
import com.oneul_tanda.flight_service.domain.entity.AirlineEntity;
import com.oneul_tanda.flight_service.domain.entity.Airport;
import com.oneul_tanda.flight_service.domain.entity.FlightEntity;
import com.oneul_tanda.flight_service.domain.repository.airline.AirlineRepository;
import com.oneul_tanda.flight_service.domain.repository.airport.AirportRepository;
import com.oneul_tanda.flight_service.domain.repository.flight.FlightRepository;
import com.oneul_tanda.flight_service.presentation.dtos.flight.FlightResponse;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FlightService {

    private final FlightRepository flightRepository;
    private final AirlineRepository airlineRepository;
    private final AirportRepository airportRepository;

    @Transactional
    public FlightResponse createFlight(CreateFlightCommand command) {

       if (flightRepository.findByFlightNumAndDepartureDate(command.getFlightNum(), command.getDepartureDate()).isPresent()) {
            throw new IllegalArgumentException("Flight with this flight number and departure date already exists");
        }

        // AirlineEntity와 Airport 엔티티 조회
        AirlineEntity airline = airlineRepository.findByCode(command.getAirlineCode())
                .orElseThrow(() -> new IllegalArgumentException("Airline not found"));
        Airport departureAirport = airportRepository.findByCode(command.getDepartureAirportCode())
                .orElseThrow(() -> new IllegalArgumentException("Departure Airport not found"));
        Airport arrivalAirport = airportRepository.findByCode(command.getArrivalAirportCode())
                .orElseThrow(() -> new IllegalArgumentException("Arrival Airport not found"));

        Duration duration = Duration.between(command.getDepartureDate(), command.getArrivalDate());
        FlightEntity flight = FlightEntity.from(
                command.getFlightNum(),
                airline,
                departureAirport,
                arrivalAirport,
                command.getDepartureDate(),
                command.getArrivalDate(),
                duration,
                command.getPrice(),
                command.getRemainingSeats()
        );

        flightRepository.save(flight);

        return FlightResponse.from(flight);
       }
    }
