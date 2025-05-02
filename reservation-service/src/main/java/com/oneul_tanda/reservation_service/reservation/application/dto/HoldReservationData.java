package com.oneul_tanda.reservation_service.reservation.application.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record HoldReservationData(
        int seatCount,
        List<PassengerDto> passengers
) {

    /**
     * 주어진 좌석 수와 승객 목록으로 새로운 HoldReservationData 인스턴스를 생성합니다.
     *
     * @param seatCount 예약할 좌석 수
     * @param passengers 예약에 포함될 승객 정보 목록
     * @return 생성된 HoldReservationData 인스턴스
     */
    public static HoldReservationData of(int seatCount, List<PassengerDto> passengers) {
        return HoldReservationData.builder()
                .seatCount(seatCount)
                .passengers(passengers)
                .build();
    }
}
