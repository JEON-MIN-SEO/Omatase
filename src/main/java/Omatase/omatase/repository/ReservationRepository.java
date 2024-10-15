package Omatase.omatase.repository;


import Omatase.omatase.entity.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity,Long> {
    // 사용자 ID에 해당하는 모든 예약 정보를 가져오는 메서드
    List<ReservationEntity> findAllByUserId(Long userId);
}
