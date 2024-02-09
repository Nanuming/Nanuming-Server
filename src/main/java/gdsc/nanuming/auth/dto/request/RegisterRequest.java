package gdsc.nanuming.auth.dto.request;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import gdsc.nanuming.member.entity.Member;
import lombok.Getter;

@Getter
public class RegisterRequest implements AuthenticationConvertible {

	private String email;
	private String nickname;
	private String provider;
	private String providerId;
	private String picture;

	public Member toMember() {
		return Member.of(
			this.email,
			this.nickname,
			this.providerId,
			this.picture
		);
	}

	@Override
	public UsernamePasswordAuthenticationToken toAuthentication() {
		return new UsernamePasswordAuthenticationToken(this.providerId, null);
	}
}
