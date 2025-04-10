package com.oneul_tanda.flight_service.domain.entity;

import com.oneul_tanda.flight_service.common.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "m_airports")
@Entity
public class Airport extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "airport_code", nullable = false)
  private String code;

  @Column(name = "airport_city", nullable = false)
  private String city;

  @Column(name = "airport_country", nullable = false)
  private String country;
}
