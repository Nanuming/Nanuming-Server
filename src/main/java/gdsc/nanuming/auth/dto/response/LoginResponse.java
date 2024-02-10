package gdsc.nanuming.auth.dto.response;

import gdsc.nanuming.jwt.dto.JwtToken;
import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginResponse {

	private Long memberId;
	private String providerId;
	private String nickname;
	private JwtToken token;

	@Builder
	private LoginResponse(Long memberId, String providerId, String nickname, JwtToken token) {
		this.memberId = memberId;
		this.providerId = providerId;
		this.nickname = nickname;
		this.token = token;
	}

	public static LoginResponse of(Long memberId, String providerId, String nickname, JwtToken token) {
		return LoginResponse.builder()
			.memberId(memberId)
			.providerId(providerId)
			.nickname(nickname)
			.token(token)
			.build();
	}
}
