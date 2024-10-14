package Omatase.omatase.repository;

import Omatase.omatase.entity.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity,Long> {
    // userId로 모든 Reservation 조회
    List<ReservationEntity> findByUserId(Long userId);
}
