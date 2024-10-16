package Omatase.omatase.service;

import Omatase.omatase.DTO.ReservationDTO;
import Omatase.omatase.entity.ReservationEntity;
import Omatase.omatase.entity.UserEntity;
import Omatase.omatase.enums.ReservationStatus;
import Omatase.omatase.exception.CustomException;
import Omatase.omatase.repository.ReservationRepository;
import Omatase.omatase.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;

    public ReservationService(ReservationRepository reservationRepository, UserRepository userRepository) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
    }

    // 사용자 ID로 모든 예약 정보를 가져오는 메서드
    public List<ReservationDTO> getAllReservationsByUserId(Long userId) {

        // 사용자 확인
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(1001, "User not found"));
        /*
        Optional<UserEntity> userEntity = userRepository.findById(userId);
        if (userEntity.isEmpty()) {
            throw new CustomException(1001, "User not found.");
        }
        */

        return reservationRepository.findAllByUserId(userId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private ReservationDTO convertToDTO(ReservationEntity reservation) {
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setRestaurant_link(reservation.getRestaurant_link());
        reservationDTO.setAdult_count(reservation.getAdult_count());
        reservationDTO.setChild_count(reservation.getChild_count());
        reservationDTO.setPrimary_date_time(reservation.getPrimary_date_time());
        reservationDTO.setSecondary_date_time(reservation.getSecondary_date_time());
        reservationDTO.setTertiary_date_time(reservation.getTertiary_date_time());
        reservationDTO.setStatus(reservation.getStatus());
        return reservationDTO;
    }

    // 새로운 예약을 추가하는 메서드
    public void addReservation(Long userId, ReservationDTO reservationDTO) {
        // 사용자 확인
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(1001, "User not found"));

        ReservationEntity reservation = new ReservationEntity();
        reservation.setUser(userEntity);
        reservation.setRestaurant_link(reservationDTO.getRestaurant_link());
        reservation.setAdult_count(reservationDTO.getAdult_count());
        reservation.setChild_count(reservationDTO.getChild_count());
        reservation.setPrimary_date_time(reservationDTO.getPrimary_date_time());
        reservation.setSecondary_date_time(reservationDTO.getSecondary_date_time());
        reservation.setTertiary_date_time(reservationDTO.getTertiary_date_time());
        reservation.setStatus(ReservationStatus.WAITING); // 기본 예약 상태 설정

        reservationRepository.save(reservation);
    }

    // 예약 확정 및 상태 확인 메서드
    public void confirmReservation(Long reservationId) {
        ReservationEntity reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new CustomException(1002, "Reservation not found"));

        // 24시간이 지나지 않았는지 확인
        if (reservation.getStatus() == ReservationStatus.AVAILABLE &&
                reservation.getAvailable_date_time().plusHours(24).isBefore(LocalDateTime.now())) {
            reservation.setStatus(ReservationStatus.CANCELED);
            reservationRepository.save(reservation);
            throw new CustomException(1006, "Reservation has been automatically canceled due to expiration.");
        }

        // 현재 상태가 AVAILABLE인지 확인
        if (reservation.getStatus() != ReservationStatus.AVAILABLE) {
            throw new CustomException(1005, "Reservation must be in AVAILABLE status to confirm.");
        }

        reservation.setStatus(ReservationStatus.CONFIRMED);
        reservationRepository.save(reservation);
    }
}