package gdsc.nanuming.auth.dto.response;

import gdsc.nanuming.jwt.dto.JwtToken;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReissueResponse {

	private String providerId;
	private String nickname;
	private JwtToken token;

	@Builder
	private ReissueResponse(String providerId, String nickname, JwtToken token) {
		this.providerId = providerId;
		this.nickname = nickname;
		this.token = token;
	}
}
