package Omatase.omatase.service;

import Omatase.omatase.DTO.InquiryDTO;
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

    // 모든 사용자 예약 정보 가져오기
    public List<ReservationDTO> getAllReservations() {
        return reservationRepository.findAll().stream()
                .map(this::convertToReservationDTO)
                .collect(Collectors.toList());
    }

    // ReservationEntity를 ReservationDTO로 변환
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

        // 사용자 정보 설정
        UserEntity user = reservation.getUser();
        if (user != null) {
            reservationDTO.setUserId(user.getId());
            reservationDTO.setUsername(user.getUsername());
        }

        return reservationDTO;
    }

    // 예약 상태 변경
    public void updateReservationStatus(ReservationStatusUpdateDTO updateDTO) {
        ReservationEntity reservation = reservationRepository.findById(updateDTO.getReservationId())
                .orElseThrow(() -> new CustomException(1002, "Reservation not found"));

        // 상태 변경 로직
        if (updateDTO.getStatus() == ReservationStatus.AVAILABLE) {
            if (updateDTO.getSelectedDateTime() == null) {
                throw new CustomException(1003, "Please select a date when changing to AVAILABLE");
            }
            reservation.setAvailable_date_time(updateDTO.getSelectedDateTime());
        }

        reservation.setStatus(updateDTO.getStatus());
        reservationRepository.save(reservation);
    }
}
