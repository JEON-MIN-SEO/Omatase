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

        //DBで照会
        UserEntity userDate = userRepository.findByUsername(username);

        // ユーザーが存在しない場合、UsernameNotFoundExceptionを発生させる
        if (userDate == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        // ユーザー情報をCustom User Detailsに変換して返却
        return new CustomUserDetails(userDate);
    }
}
