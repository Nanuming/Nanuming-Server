package gdsc.nanuming;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableJpaAuditing
@SpringBootApplication
public class NanumingApplication {

	public static void main(String[] args) {

		ZoneId systemDefaultZoneId = ZoneId.systemDefault();
		LocalDateTime now = LocalDateTime.now(systemDefaultZoneId);
		log.info("Application is running in timezone: {} with current time: {}", systemDefaultZoneId, now);

		SpringApplication.run(NanumingApplication.class, args);
	}

}
