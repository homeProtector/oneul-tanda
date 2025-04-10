package com.oneul_tanda.reservation_service.reservation.domain.entity;


import com.oneul_tanda.reservation_service.ticket.domain.entity.Ticket;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "m_reservations")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "reservation_id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "total_price", nullable = false)
    private BigDecimal totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "reservation_status", nullable = false)
    private ReservationStatus status;

    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ticket> ticketList;

}
