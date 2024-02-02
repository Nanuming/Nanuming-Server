package gdsc.nanuming.jwt.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class JwtToken {

	private String accessToken;
	private String refreshToken;

	@Builder
	private JwtToken(String accessToken, String refreshToken) {
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}

	public static JwtToken of(String accessToken, String refreshToken) {
		return JwtToken.builder()
			.accessToken(accessToken)
			.refreshToken(refreshToken)
			.build();
	}
}
