package Omatase.omatase.repository;

import Omatase.omatase.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    // メールでユーザーの存在を確認
    boolean existsByUsername(String username);

    // メールでユーザーを探索
    UserEntity findByUsername(String username);
}
