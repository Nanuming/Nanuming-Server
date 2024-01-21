package gdsc.nanuming.secretmanager;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SecretManagerTest {

	private static final Logger logger = LoggerFactory.getLogger(SecretManagerTest.class);

	@Value("${sm://TEST_DB_HOST}")
	private String mySecretDbHost;

	@Test
	public void checkSecretDbHost() {
		if (mySecretDbHost != null) {
			logger.info("Secret DB Host length: {}", mySecretDbHost);
		} else {
			logger.info("NONE");
		}
	}

}
