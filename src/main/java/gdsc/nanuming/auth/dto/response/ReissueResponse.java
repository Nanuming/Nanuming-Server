package gdsc.nanuming.auth.dto.response;

import gdsc.nanuming.jwt.dto.JwtToken;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReissueResponse {

	private Long memberId;
	private String providerId;
	private String nickname;
	private JwtToken token;

	@Builder
	private ReissueResponse(Long memberId, String providerId, String nickname, JwtToken token) {
		this.memberId = memberId;
		this.providerId = providerId;
		this.nickname = nickname;
		this.token = token;
	}

	public static ReissueResponse of(Long memberId, String providerId, String nickname, JwtToken token) {
		return ReissueResponse.builder()
			.memberId(memberId)
			.providerId(providerId)
			.nickname(nickname)
			.token(token)
			.build();

	}
}
