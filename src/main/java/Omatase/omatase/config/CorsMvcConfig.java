package Omatase.omatase.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 모든 URL 경로에 대해 적용
                .allowedOriginPatterns("*") // 모든 Origin 허용
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS") // 허용되는 HTTP 메서드
                .allowedHeaders("*") // 모든 요청 헤더 허용
                .exposedHeaders("Authorization", "Custom-Header") // 클라이언트에서 읽을 수 있는 응답 헤더
                .allowCredentials(true) // 인증 정보(쿠키 등) 허용
                .maxAge(3600); // 사전 요청(pre-flight request)의 캐시 지속 시간 (초 단위)
    }
}
