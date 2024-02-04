package gdsc.nanuming.jwt.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import lombok.Builder;
import lombok.Getter;

@Getter
@RedisHash(value = "refreshToken")
public class RefreshToken {

	@Id
	private String refreshToken;

	private String providerId;

	@TimeToLive
	private Long tokenPeriod;

	@Builder
	private RefreshToken(final String refreshToken, final String providerId, final Long tokenPeriod) {
		this.refreshToken = refreshToken;
		this.providerId = providerId;
		this.tokenPeriod = tokenPeriod;
	}

	public static RefreshToken of(String refreshToken, String providerId, Long tokenPeriod) {
		return RefreshToken.builder()
			.refreshToken(refreshToken)
			.providerId(providerId)
			.tokenPeriod(tokenPeriod)
			.build();
	}
}
