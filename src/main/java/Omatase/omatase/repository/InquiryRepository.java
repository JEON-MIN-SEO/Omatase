package Omatase.omatase.repository;

import Omatase.omatase.entity.InquiryEntity;
import Omatase.omatase.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InquiryRepository extends JpaRepository<InquiryEntity, Long> {
    // 特定ユーザーのすべての問い合わせ照会
    List<InquiryEntity> findAllByUserAndDeletedIsFalse(UserEntity user);

    // すべての問い合わせを照会できるメソッド（削除の有無を無視）
    //List<InquiryEntity> findAll();
    Optional<List<InquiryEntity>> findByUserId(Long userId);
}