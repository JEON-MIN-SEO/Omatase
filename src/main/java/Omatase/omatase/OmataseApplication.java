package Omatase.omatase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling // スケジューリング活性化
@EnableJpaAuditing // 時間自動保存
public class OmataseApplication {

	public static void main(String[] args) {
		SpringApplication.run(OmataseApplication.class, args);
	}

}
