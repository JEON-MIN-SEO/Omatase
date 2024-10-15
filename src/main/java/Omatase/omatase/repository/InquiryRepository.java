package Omatase.omatase.repository;

import Omatase.omatase.entity.InquiryEntity;
import Omatase.omatase.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InquiryRepository extends JpaRepository<InquiryEntity, Long> {
    // 특정 사용자의 모든 문의 조회
    List<InquiryEntity> findAllByUserAndDeletedIsFalse(UserEntity user);

    // 모든 문의를 조회할 수 있는 메서드 (삭제 여부 무시)
    //List<InquiryEntity> findAll();
    Optional<List<InquiryEntity>> findByUserId(Long userId);
}