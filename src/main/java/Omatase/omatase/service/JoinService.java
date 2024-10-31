package Omatase.omatase.service;

import Omatase.omatase.DTO.UserDTO;
import Omatase.omatase.entity.UserEntity;
import Omatase.omatase.exception.CustomException;
import Omatase.omatase.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JoinService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public JoinService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void JoinProcess(UserDTO userDTO) {
        // 電子メールの有効性チェック:nullまたは値がないことを確認
        if (userDTO.getUsername() == null || userDTO.getUsername().isEmpty()) {
            throw new CustomException(3001, "Email는 필수 입니다.");
        }

        // パスワードの有効性チェック:nullまたは値がないか確認
        if (userDTO.getPassword() == null || userDTO.getPassword().isEmpty()) {
            throw new CustomException(3002, "Password는 필수 입니다.");
        }

        //DBへ同じユーザがあるか確認するメソッドが必要、UserRepositoryでexistsByメソッドを使う
        if (userRepository.existsByUsername(userDTO.getUsername())) {
            throw new CustomException(3003, "이미 회원가입이 되어있습니다."); // 例外処理、エラコード、メッセージ
        } else {
            UserEntity data = new UserEntity();
            data.setUsername(userDTO.getUsername());
            data.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
            data.setRole("ROLE_USER");
            userRepository.save(data);
        }
    }

    public void AdminJoinProcess(UserDTO userDTO) {
        // 電子メールの有効性チェック:nullまたは値がないことを確認
        if (userDTO.getUsername() == null || userDTO.getUsername().isEmpty()) {
            throw new CustomException(3001, "Email는 필수 입니다.");
        }

        // パスワードの有効性チェック:nullまたは値がないか確認
        if (userDTO.getPassword() == null || userDTO.getPassword().isEmpty()) {
            throw new CustomException(3002, "Password는 필수 입니다.");
        }

        //DBへ同じユーザがあるか確認するメソッドが必要、UserRepositoryでexistsByメソッドを使う
        if (userRepository.existsByUsername(userDTO.getUsername())) {
            throw new CustomException(3003, "이미 회원가입이 되어있습니다."); // 例外処理、エラコード、メッセージ
        } else {
            UserEntity data = new UserEntity();
            data.setUsername(userDTO.getUsername());
            data.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
            data.setRole("ROLE_ADMIN");
            userRepository.save(data);
        }
    }
}