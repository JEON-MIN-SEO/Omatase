package Omatase.omatase.service;

import Omatase.omatase.DTO.CustomUserDetails;
import Omatase.omatase.entity.UserEntity;
import Omatase.omatase.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //DB 에서 조회
        UserEntity userDate = userRepository.findByUsername(username);

        // 사용자가 존재하지 않을 경우 UsernameNotFoundException을 발생시킴
        if (userDate == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        // 사용자 정보를 CustomUserDetails로 변환하여 반환
        return new CustomUserDetails(userDate);
    }
}
