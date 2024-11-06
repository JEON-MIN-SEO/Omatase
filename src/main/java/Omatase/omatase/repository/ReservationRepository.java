package Omatase.omatase.repository;


import Omatase.omatase.entity.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity,Long> {
    // ユーザーIDに該当するすべての予約情報を取得するメソッド
    List<ReservationEntity> findAllByUserId(Long userId);
}
