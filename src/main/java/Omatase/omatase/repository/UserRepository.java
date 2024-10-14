package Omatase.omatase.repository;

import Omatase.omatase.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    // 이메일로 사용자 존재 여부 확인
    boolean existsByUsername(String username);

    // 이메일로 사용자 찾기
    UserEntity findByUsername(String username);
}
