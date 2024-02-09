package gdsc.nanuming.redis.entity;

import org.springframework.data.annotation.Id;

import lombok.Builder;
import lombok.Getter;

@Getter
public class RefreshToken {

	@Id
	private String providerId;

	private String refreshToken;

	@Builder
	private RefreshToken(final String providerId, final String refreshToken) {
		this.providerId = providerId;
		this.refreshToken = refreshToken;
	}

	public static RefreshToken of(String providerId, String refreshToken) {
		return RefreshToken.builder()
			.providerId(providerId)
			.refreshToken(refreshToken)
			.build();
	}

	public RefreshToken updateRefreshToken(String newRefreshToken) {
		this.refreshToken = newRefreshToken;
		return this;
	}

}
