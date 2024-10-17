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

        if (userDate != null ){
            return new CustomUserDetails(userDate);
        }
        return null;
    }
}
