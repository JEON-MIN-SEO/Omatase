package Omatase.omatase.service;

import Omatase.omatase.DTO.CustomOAuth2User;
import Omatase.omatase.DTO.KakaoResponse;
import Omatase.omatase.DTO.OAuth2Response;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println(oAuth2User.getAttributes());

        //registrationId는 어떤 클라이언트인지 체크
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Response oAuth2Response = null;
        if (registrationId.equals("kakao")) {

            oAuth2Response = new KakaoResponse(oAuth2User.getAttributes());

        }
        else {

            return null;
        }

        String role = "ROLE_USER";

        return new CustomOAuth2User(oAuth2Response, role);
    }
}
