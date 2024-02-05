package gdsc.nanuming.redis.entity;

import org.springframework.data.annotation.Id;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BlacklistToken {

	@Id
	private String accessToken;
	private String status;

	@Builder
	private BlacklistToken(final String accessToken, final String status) {
		this.accessToken = accessToken;
		this.status = status;
	}

	public static BlacklistToken of(String accessToken, String status) {
		return BlacklistToken.builder()
			.accessToken(accessToken)
			.status(status)
			.build();
	}
}
