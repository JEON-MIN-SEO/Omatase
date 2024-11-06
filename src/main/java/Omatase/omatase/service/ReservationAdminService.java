package Omatase.omatase.service;

import Omatase.omatase.DTO.ReservationDTO;
import Omatase.omatase.DTO.ReservationStatusUpdateDTO;
import Omatase.omatase.entity.ReservationEntity;
import Omatase.omatase.entity.UserEntity;
import Omatase.omatase.enums.ReservationStatus;
import Omatase.omatase.exception.CustomException;
import Omatase.omatase.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationAdminService {

    private final ReservationRepository reservationRepository;

    public ReservationAdminService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    // すべてのユーザー予約情報の取得
    public List<ReservationDTO> getAllReservations() {
        return reservationRepository.findAll().stream()
                .map(this::convertToReservationDTO)
                .collect(Collectors.toList());
    }

    // ReservationEntityをReservationDTOに変換
    private ReservationDTO convertToReservationDTO(ReservationEntity reservation) {
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setReservationId(reservation.getId());
        reservationDTO.setRestaurant_link(reservation.getRestaurant_link());
        reservationDTO.setAdult_count(reservation.getAdult_count());
        reservationDTO.setChild_count(reservation.getChild_count());
        reservationDTO.setPrimary_date_time(reservation.getPrimary_date_time());
        reservationDTO.setSecondary_date_time(reservation.getSecondary_date_time());
        reservationDTO.setTertiary_date_time(reservation.getTertiary_date_time());
        reservationDTO.setAvailable_date_time(reservation.getAvailable_date_time());
        reservationDTO.setStatus(reservation.getStatus());
        reservationDTO.setCreatedAt(reservation.getCreatedAt());
        reservationDTO.setModifiedAt(reservation.getModifiedAt());

        // ユーザー情報設定
        UserEntity user = reservation.getUser();
        if (user != null) {
            reservationDTO.setUserId(user.getId());
            reservationDTO.setUsername(user.getUsername());
        }

        return reservationDTO;
    }

    // 予約状態の変更
    public void updateReservationStatus(ReservationStatusUpdateDTO updateDTO) {
        ReservationEntity reservation = reservationRepository.findById(updateDTO.getReservationId())
                .orElseThrow(() -> new CustomException(1002, "예약을 발견하지 못했습니다."));

        //　状態変更ロジック
        if (updateDTO.getStatus() == ReservationStatus.AVAILABLE) {
            if (updateDTO.getSelectedDateTime() == null) {
                throw new CustomException(1003, "변경할 시간을 입력해주세요");
            }
            reservation.setAvailable_date_time(updateDTO.getSelectedDateTime());
        }

        reservation.setStatus(updateDTO.getStatus());
        reservationRepository.save(reservation);
    }
}
