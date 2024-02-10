package gdsc.nanuming.auth.dto.request;

import gdsc.nanuming.member.entity.Member;
import lombok.Getter;

@Getter
public class RegisterRequest {

	private String idToken;
	private String nickname;

	public Member toMember(String providerId) {
		return Member.of(
			this.nickname,
			providerId
		);
	}

}
