package gdsc.nanuming.auth.dto.response;

import gdsc.nanuming.jwt.dto.JwtToken;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RegisterResponse {

	private String providerId;
	private String nickname;
	private JwtToken token;

	@Builder
	private RegisterResponse(String providerId, String nickname, JwtToken token) {
		this.providerId = providerId;
		this.nickname = nickname;
		this.token = token;
	}

	public static RegisterResponse of(String providerId, String nickname, JwtToken token) {
		return RegisterResponse.builder()
			.providerId(providerId)
			.nickname(nickname)
			.token(token)
			.build();
	}
}
