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

    // 1時間ごとに予約状態を確認し、必要に応じてCANCELEDに変更
    @Scheduled(fixedRate = 3600000) // 1時間ごとに
    public void checkAndCancelExpiredReservations() {
        System.out.println("Scheduler is running..."); // スケジューラ実行確認用ログ
        List<ReservationEntity> reservations = reservationRepository.findAll();

        for (ReservationEntity reservation : reservations) {
            // ステータスがAVAILABLEの予約についてのみ確認
            if (reservation.getStatus() == ReservationStatus.AVAILABLE) {
                // 予約がAVAILABLE状態に変更された後、24時間が経過したかを確認
                if (reservation.getAvailable_date_time() != null &&
                        reservation.getModifiedAt().plusHours(24).isBefore(LocalDateTime.now())) {

                    // 状態をCANCELEDに変更
                    reservation.setStatus(ReservationStatus.CANCELED);
                    reservationRepository.save(reservation);
                }
            }
        }
    }
}
