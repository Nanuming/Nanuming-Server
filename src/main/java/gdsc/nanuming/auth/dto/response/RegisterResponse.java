package gdsc.nanuming.auth.dto.response;

import gdsc.nanuming.jwt.dto.JwtToken;
import gdsc.nanuming.member.entity.Member;
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

	public static RegisterResponse of(Member member, JwtToken token) {
		return RegisterResponse.builder()
			.providerId(member.getProviderId())
			.nickname(member.getNickname())
			.token(token)
			.build();
	}
}
