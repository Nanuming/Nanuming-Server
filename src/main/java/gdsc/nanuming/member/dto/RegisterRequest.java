package gdsc.nanuming.member.dto;

import gdsc.nanuming.member.entity.Member;
import lombok.Getter;

@Getter
public class RegisterRequest {

	private String email;
	private String nickname;
	private String provider;
	private String providerId;
	private String picture;

	public Member toEntity() {
		return Member.of(
			this.email,
			this.nickname,
			this.providerId,
			this.picture
		);
	}
}
