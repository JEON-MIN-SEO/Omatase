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

    public void UserJoinProcess(UserDTO userDTO) {
        // 이메일 유효성 검사: null 또는 빈 값인지 확인
        if (userDTO.getUsername() == null || userDTO.getUsername().isEmpty()) {
            throw new CustomException(2001, "Email값은 필수 입니다.");
        }

        // 비밀번호 유효성 검사: null 또는 빈 값인지 확인
        if (userDTO.getPassword() == null || userDTO.getPassword().isEmpty()) {
            throw new CustomException(2002, "비밀번호는 필수 입니다.");
        }

        //DBへ同じユーザがあるか確認するメソッドが必要、UserRepositoryでexistsByメソッドを使う
        if (userRepository.existsByUsername(userDTO.getUsername())) {
            throw new CustomException(2003, "이미 있는 이메일 입니다."); // 例外処理、エラコード、メッセージ
        } else {
            UserEntity data = new UserEntity();
            data.setUsername(userDTO.getUsername());
            data.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
            data.setRole("ROLE_USER");
            userRepository.save(data);
        }
    }

    public void AdminJoinProcess(UserDTO userDTO) {
        // 이메일 유효성 검사: null 또는 빈 값인지 확인
        if (userDTO.getUsername() == null || userDTO.getUsername().isEmpty()) {
            throw new CustomException(2001, "Email값은 필수 입니다.");
        }

        // 비밀번호 유효성 검사: null 또는 빈 값인지 확인
        if (userDTO.getPassword() == null || userDTO.getPassword().isEmpty()) {
            throw new CustomException(2002, "비밀번호는 필수 입니다.");
        }

        //DBへ同じユーザがあるか確認するメソッドが必要、UserRepositoryでexistsByメソッドを使う
        if (userRepository.existsByUsername(userDTO.getUsername())) {
            throw new CustomException(2003, "이미 있는 이메일 입니다."); // 例外処理、エラコード、メッセージ
        } else {
            UserEntity data = new UserEntity();
            data.setUsername(userDTO.getUsername());
            data.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
            data.setRole("ROLE_ADMIN");
            userRepository.save(data);
        }
    }
}
