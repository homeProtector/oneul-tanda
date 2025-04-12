package com.oneul_tanda.flight_service.infrastructure.repository;

import com.oneul_tanda.flight_service.domain.entity.Flight;
import com.oneul_tanda.flight_service.domain.repository.FlightRepository;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightRepositoryImpl extends JpaRepository<Flight, UUID>, FlightRepository {
}
