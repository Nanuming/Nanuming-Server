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
	private String providerId;

	private String refreshToken;

	@TimeToLive
	private Long tokenPeriod;

	@Builder
	private RefreshToken(final String providerId, final String refreshToken, final Long tokenPeriod) {
		this.providerId = providerId;
		this.refreshToken = refreshToken;
		this.tokenPeriod = tokenPeriod;
	}

	public static RefreshToken of(String providerId, String refreshToken, Long tokenPeriod) {
		return RefreshToken.builder()
			.providerId(providerId)
			.refreshToken(refreshToken)
			.tokenPeriod(tokenPeriod)
			.build();
	}
}
