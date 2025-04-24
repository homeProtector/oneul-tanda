package com.oneul_tanda.flight_service.application.service.flight;

import com.oneul_tanda.flight_service.application.dtos.flight.CreateFlightCommand;
import com.oneul_tanda.flight_service.application.dtos.flight.UpdateFlightCommand;
import com.oneul_tanda.flight_service.domain.entity.AirlineEntity;
import com.oneul_tanda.flight_service.domain.entity.AirportEntity;
import com.oneul_tanda.flight_service.domain.entity.FlightEntity;
import com.oneul_tanda.flight_service.domain.repository.airline.AirlineRepository;
import com.oneul_tanda.flight_service.domain.repository.airport.AirportRepository;
import com.oneul_tanda.flight_service.domain.repository.flight.FlightRepository;
import com.oneul_tanda.flight_service.domain.repository.flight.FlightRepositoryCustom;
import com.oneul_tanda.flight_service.presentation.dtos.flight.FlightDetailResponse;
import com.oneul_tanda.flight_service.presentation.dtos.flight.FlightResponse;
import com.oneul_tanda.flight_service.util.PagingUtil;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FlightService {

    private final FlightRepository flightRepository;
    private final FlightRepositoryCustom flightRepositoryCustom;
    private final AirlineRepository airlineRepository;
    private final AirportRepository airportRepository;

    @Cacheable(value = "flights", key = "#flightId")
    public FlightDetailResponse getFlight(UUID flightId) {

        FlightEntity flight = getFlightById(flightId);

        return FlightDetailResponse.from(flight);
    }

    public Page<FlightResponse> searchFlights(String departureAirport, String arrivalAirport,
                                              LocalDateTime departureDate, Integer requiredSeats,
                                              Pageable pageable, String userRole
    ) {
        validateUserRole(userRole);

        Pageable adjusted = PagingUtil.adjustPageable(pageable);
        Page<FlightEntity> flights = flightRepositoryCustom.searchFlights(
                departureAirport, arrivalAirport,
                departureDate, requiredSeats, adjusted
        );

        return flights.map(FlightResponse::from);
    }

    @Transactional
    @CacheEvict(value = "flights", allEntries = true) // 캐시 무효화
    public FlightResponse createFlight(CreateFlightCommand flightCommand, UUID userId, String userRole) {
        validateUserRole(userRole);

        if (flightRepository.findByFlightNumAndDepartureDate(flightCommand.getFlightNum(),
                        flightCommand.getDepartureDate())
                .isPresent()) {
            throw new IllegalArgumentException("Flight with this flight number and departure date already exists");
        }

        // AirlineEntity와 Airport 엔티티 조회
        AirlineEntity airline = getAirlineForCreateFlight(flightCommand);
        AirportEntity departureAirport = getDepartureAirportForCreateFlight(flightCommand);
        AirportEntity arrivalAirport = getArrivalAirportForCreateFlight(flightCommand);

        Duration duration = Duration.between(flightCommand.getDepartureDate(), flightCommand.getArrivalDate());

        FlightEntity flight = FlightEntity.from(
                flightCommand.getFlightNum(),
                airline,
                departureAirport,
                arrivalAirport,
                flightCommand.getDepartureDate(),
                flightCommand.getArrivalDate(),
                duration,
                flightCommand.getPrice(),
                flightCommand.getRemainingSeats()
        );

        flightRepository.save(flight);
        airline.updateCreationInfo(userId);

        return FlightResponse.from(flight);
    }

    @Transactional
    @CacheEvict(value = "flights", key = "#flightCommand.flightId") // 캐시 무효화
    public FlightResponse updateFlight(UpdateFlightCommand flightCommand, UUID userId, String userRole) {
        validateUserRole(userRole);
        FlightEntity flight = getFlightById(flightCommand.getFlightId());

        AirlineEntity airline = getAirlineForUpdateFlight(flightCommand);
        AirportEntity departureAirport = getDepartureAirportForUpdateFlight(flightCommand);
        AirportEntity arrivalAirport = getArrivalAirportForUpdateFlight(flightCommand);

        Duration duration = Duration.between(flightCommand.getDepartureDate(), flightCommand.getArrivalDate());

        flight.updateOf(
                flightCommand.getFlightNum(),
                airline,
                departureAirport,
                arrivalAirport,
                flightCommand.getDepartureDate(),
                flightCommand.getArrivalDate(),
                duration,
                flightCommand.getPrice(),
                flightCommand.getRemainingSeats()
        );

        flight.updateModificationInfo(userId);

        return FlightResponse.from(flight);
    }

    @Transactional
    @CacheEvict(value = "flights", key = "#flightId") // 캐시 무효화
    public void deleteFlight(UUID flightId, UUID userId, String userRole) {
        validateUserRole(userRole);

        FlightEntity flight = getFlightById(flightId);

        flight.updateDeletionInfo(userId);
    }

    @Transactional
    @CacheEvict(value = "flights", key = "#flightId") // 캐시 무효화
    public void decreaseSeats(UUID flightId, Integer requiredSeats) {
        FlightEntity flight = getFlightById(flightId);

        flight.decreaseSeatCount(requiredSeats);
    }

    @Transactional
    @CacheEvict(value = "flights", key = "#flightId") // 캐시 무효화
    public void increaseSeats(UUID flightId, Integer requiredSeats) {
        FlightEntity flight = getFlightById(flightId);

        flight.increaseSeatCount(requiredSeats);
    }

    private AirportEntity getArrivalAirportForUpdateFlight(UpdateFlightCommand flightCommand) {
        return airportRepository.findByCode(flightCommand.getArrivalAirportCode())
                .orElseThrow(() -> new IllegalArgumentException("Arrival Airport not found"));
    }

    private AirportEntity getDepartureAirportForUpdateFlight(UpdateFlightCommand flightCommand) {
        return airportRepository.findByCode(flightCommand.getDepartureAirportCode())
                .orElseThrow(() -> new IllegalArgumentException("Departure Airport not found"));
    }

    private AirlineEntity getAirlineForUpdateFlight(UpdateFlightCommand flightCommand) {
        return airlineRepository.findByCode(flightCommand.getAirlineCode())
                .orElseThrow(() -> new IllegalArgumentException("Airline not found"));
    }

    private AirportEntity getArrivalAirportForCreateFlight(CreateFlightCommand flightCommand) {
        return airportRepository.findByCode(flightCommand.getArrivalAirportCode())
                .orElseThrow(() -> new IllegalArgumentException("Arrival Airport not found"));
    }

    private AirportEntity getDepartureAirportForCreateFlight(CreateFlightCommand flightCommand) {
        return airportRepository.findByCode(flightCommand.getDepartureAirportCode())
                .orElseThrow(() -> new IllegalArgumentException("Departure Airport not found"));
    }

    private AirlineEntity getAirlineForCreateFlight(CreateFlightCommand flightCommand) {
        return airlineRepository.findByCode(flightCommand.getAirlineCode())
                .orElseThrow(() -> new IllegalArgumentException("Airline not found"));
    }

    private FlightEntity getFlightById(UUID flightId) {
        return flightRepository.findById(flightId)
                .orElseThrow(() -> new IllegalArgumentException("Flight not found"));
    }

    private void validateUserRole(String userRole) {
        if(userRole.equals("CUSTOMER")) {
            throw new IllegalArgumentException("Access denied");
        }
    }
}
