package Omatase.omatase.service;

import Omatase.omatase.entity.ReservationEntity;
import Omatase.omatase.enums.ReservationStatus;
import Omatase.omatase.repository.ReservationRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class ReservationScheduler {

    private final ReservationRepository reservationRepository;

    public ReservationScheduler(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    // 매 1시간마다 예약 상태를 확인하고, 필요한 경우 CANCELED로 변경
    @Scheduled(fixedRate = 3600000) // 1시간마다 실행
    public void checkAndCancelExpiredReservations() {
        List<ReservationEntity> reservations = reservationRepository.findAll();

        for (ReservationEntity reservation : reservations) {
            // 상태가 AVAILABLE인 예약에 대해서만 확인
            if (reservation.getStatus() == ReservationStatus.AVAILABLE) {
                // 예약이 AVAILABLE 상태로 변경된 후 24시간이 지났는지 확인
                if (reservation.getAvailable_date_time() != null &&
                        reservation.getModifiedAt().plusHours(24).isBefore(LocalDateTime.now())) {

                    // 상태를 CANCELED로 변경
                    reservation.setStatus(ReservationStatus.CANCELED);
                    reservationRepository.save(reservation);
                }
            }
        }
    }
}
