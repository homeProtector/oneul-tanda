package com.oneul_tanda.flight_service.presentation.controller;

import com.oneul_tanda.flight_service.presentation.dtos.UpdateAirportRequest;
import com.oneul_tanda.flight_service.application.service.AirportService;
import com.oneul_tanda.flight_service.presentation.dtos.AirportRequest;
import com.oneul_tanda.flight_service.application.dtos.AirportResponse;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/airports")
@RequiredArgsConstructor
public class AirportController {

    private final AirportService airportService;

    @PostMapping
    public ResponseEntity<AirportResponse> createAirport(
            @RequestBody @Valid AirportRequest request
    ) {
        AirportResponse response = airportService.createAirport(request.toCommand());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{airportId}")
    public ResponseEntity<AirportResponse> updateAirport(
            @PathVariable UUID airportId,
            @RequestBody @Valid UpdateAirportRequest request
    ) {
        AirportResponse response = airportService.updateAirport(request.toCommand(airportId));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{airportId}")
    public ResponseEntity<Void> deleteAirport(
            @PathVariable UUID airportId
    ) {
        airportService.deleteAirport(airportId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
