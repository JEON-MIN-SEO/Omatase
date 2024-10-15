package Omatase.omatase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling // 스케줄링 활성화
@EnableJpaAuditing // 시간 자동 저장
public class OmataseApplication {

	public static void main(String[] args) {
		SpringApplication.run(OmataseApplication.class, args);
	}

}
