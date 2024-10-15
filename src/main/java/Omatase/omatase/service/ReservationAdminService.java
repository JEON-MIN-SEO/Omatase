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

    // 모든 사용자 예약 정보 가져오기
    public List<ReservationDTO> getAllReservations() {
        return reservationRepository.findAll().stream()
                .map(this::convertToDTO) // convertToDTO 메서드 참조
                .collect(Collectors.toList());
    }

    // convertToDTO 메서드
    private ReservationDTO convertToDTO(ReservationEntity reservation) {


        ReservationDTO reservationDTO = new ReservationDTO();
        // 예약 ID 설정
        reservationDTO.setReservationId(reservation.getId());
        reservationDTO.setRestaurant_link(reservation.getRestaurant_link());
        reservationDTO.setAdult_count(reservation.getAdult_count());
        reservationDTO.setChild_count(reservation.getChild_count());
        reservationDTO.setPrimary_date_time(reservation.getPrimary_date_time());
        reservationDTO.setSecondary_date_time(reservation.getSecondary_date_time());
        reservationDTO.setTertiary_date_time(reservation.getTertiary_date_time());
        reservationDTO.setStatus(reservation.getStatus());
        reservationDTO.setCreatedAt(reservation.getCreatedAt());

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
            // AVAILABLE 상태로 변경 시 날짜가 선택되었는지 확인
            if (updateDTO.getSelectedDateTime() == null) {
                throw new CustomException(1003, "Please select a date when changing to AVAILABLE");
            }
            // 선택한 날짜 설정
            reservation.setAvailable_date_time(updateDTO.getSelectedDateTime()); // 선택한 날짜를 available_date_time에 설정
        }

        reservation.setStatus(updateDTO.getStatus());
        reservationRepository.save(reservation);
    }

}
