package com.oneul_tanda.reservation_service.reservation.application.service;

import com.oneul_tanda.reservation_service.reservation.application.command.ConfirmReservationCommand;
import com.oneul_tanda.reservation_service.reservation.application.command.ConfirmReservationCommandV2;
import com.oneul_tanda.reservation_service.reservation.application.command.CreateHoldReservationCommand;
import com.oneul_tanda.reservation_service.reservation.application.command.CreateReservationCommand;
import com.oneul_tanda.reservation_service.reservation.presentation.dto.response.create.CreateHoldReservationResponseDto;
import com.oneul_tanda.reservation_service.reservation.presentation.dto.response.create.CreateReservationResponseDto;
import com.oneul_tanda.reservation_service.reservation.presentation.dto.response.update.CancelReservationResponseDto;
import com.oneul_tanda.reservation_service.reservation.presentation.dto.response.update.ConfirmReservationResponseDto;
import com.oneul_tanda.reservation_service.reservation.presentation.dto.response.read.ReadReservationResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ReservationService {

    /**
 * 예약 보류 요청을 생성하고 해당 보류 예약의 정보를 반환합니다.
 *
 * @param command 보류 예약 생성에 필요한 정보가 담긴 명령 객체
 * @return 생성된 보류 예약의 상세 정보를 담은 응답 DTO
 */
CreateHoldReservationResponseDto createHoldReservation(CreateHoldReservationCommand command);

    /**
 * 예약 보류를 생성합니다. (버전 2)
 *
 * @param command 예약 보류 생성에 필요한 정보가 담긴 명령 객체
 */
void createHoldReservationV2(CreateHoldReservationCommand command);

    /****
 * 예약을 생성하고 예약 정보를 반환합니다.
 *
 * @param command 예약 생성에 필요한 정보를 담은 명령 객체
 * @return 생성된 예약의 상세 정보를 담은 DTO
 */
CreateReservationResponseDto createReservation(CreateReservationCommand command);

    /**
 * 지정된 예약 ID에 해당하는 예약 정보를 조회합니다.
 *
 * @param reservationId 조회할 예약의 UUID
 * @return 예약 상세 정보를 담은 응답 DTO
 */
ReadReservationResponseDto readReservation(UUID reservationId);

    Page<ReadReservationResponseDto> readAllReservation(Pageable pageable);

    /**
 * 예약을 확정하고 확정된 예약 정보를 반환합니다.
 *
 * @param command 예약 확정에 필요한 정보를 담은 명령 객체
 * @return 확정된 예약의 상세 정보를 담은 DTO
 */
ConfirmReservationResponseDto confirmReservation(ConfirmReservationCommand command);

    /**
 * 새로운 명령 객체를 사용하여 예약을 확정하고 확정 결과 정보를 반환합니다.
 *
 * @param command 예약 확정에 필요한 정보를 담은 명령 객체
 * @return 예약 확정 결과를 담은 응답 DTO
 */
ConfirmReservationResponseDto confirmReservationV2(ConfirmReservationCommandV2 command);

    /**
 * 지정된 예약 ID에 해당하는 예약을 취소합니다.
 *
 * @param reservationId 취소할 예약의 UUID
 * @return 예약 취소 결과를 담은 응답 DTO
 */
CancelReservationResponseDto cancelReservation(UUID reservationId);
}
