package Omatase.omatase.DTO;

import java.util.Map;

public class KakaoResponse implements OAuth2Response{

    private final Map<String, Object> attribute;

    public KakaoResponse(Map<String, Object> attribute) {

        this.attribute = attribute;
    }

    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public String getProviderId() {
        return attribute.get("id").toString();
    }

    @Override
    public String getName() {
        // "properties" 또는 "kakao_account"에서 nickname 추출
        Map<String, Object> properties = (Map<String, Object>) attribute.get("properties");
        if (properties != null && properties.get("nickname") != null) {
            return properties.get("nickname").toString();
        }

        Map<String, Object> kakaoAccount = (Map<String, Object>) attribute.get("kakao_account");
        if (kakaoAccount != null) {
            Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
            if (profile != null && profile.get("nickname") != null) {
                return profile.get("nickname").toString();
            }
        }

        return "Unknown"; // nickname이 없을 경우 기본값
    }
}
