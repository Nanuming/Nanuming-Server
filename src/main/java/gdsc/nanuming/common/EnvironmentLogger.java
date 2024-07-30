package gdsc.nanuming.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class EnvironmentLogger {

	@Value("${TEST_DB_URL}")
	private String dbUrl;

	@Value("${TEST_DB_USER}")
	private String dbUser;

	@Value("${TEST_DB_PASSWORD}")
	private String dbPassword;

	@PostConstruct
	public void logEnvVariables() {
		System.out.println("TEST_DB_URL: " + dbUrl);
		System.out.println("TEST_DB_USER: " + dbUser);
		System.out.println("TEST_DB_PASSWORD: " + dbPassword);
	}
}
