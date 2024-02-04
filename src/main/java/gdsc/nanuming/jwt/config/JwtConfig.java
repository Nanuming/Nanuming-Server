package gdsc.nanuming.jwt.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

	@Value("${sm://JWT_ACCESS_TOKEN_PERIOD}")
	private String accessTokenPeriod;

	@Value("${sm://JWT_REFRESH_TOKEN_PERIOD}")
	private String refreshTokenPeriod;

	public Long getAccessTokenPeriod() {
		return Long.parseLong(accessTokenPeriod);
	}

	public Long getRefreshTokenPeriod() {
		return Long.parseLong(refreshTokenPeriod);
	}

}
