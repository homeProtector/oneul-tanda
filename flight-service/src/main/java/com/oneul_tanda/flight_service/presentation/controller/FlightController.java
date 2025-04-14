package com.oneul_tanda.flight_service.presentation.controller;

import com.oneul_tanda.flight_service.application.service.flight.FlightService;
import com.oneul_tanda.flight_service.presentation.dtos.flight.CreateFlightRequest;
import com.oneul_tanda.flight_service.presentation.dtos.flight.FlightResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/flights")
public class FlightController {

    private final FlightService flightService;

    @PostMapping
    public ResponseEntity<FlightResponse> createFlight(
            @RequestBody @Valid CreateFlightRequest request
    ) {
        FlightResponse response = flightService.createFlight(request.toCommand());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
