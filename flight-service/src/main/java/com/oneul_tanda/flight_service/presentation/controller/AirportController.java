package com.oneul_tanda.flight_service.presentation.controller;

import com.oneul_tanda.flight_service.application.service.AirportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/airports")
@RequiredArgsConstructor
public class AirportController {

  private final AirportService airportService;

}
