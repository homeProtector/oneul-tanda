package com.oneul_tanda.flight_service.infrastructure.repository.airline;

import com.oneul_tanda.flight_service.domain.entity.AirLine;
import com.oneul_tanda.flight_service.domain.repository.airline.AirlineRepository;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirlineRepositoryImpl extends JpaRepository<AirLine, UUID>, AirlineRepository {
}
