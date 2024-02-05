package gdsc.nanuming.auth.dto.request;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import lombok.Getter;

@Getter
public class ReissueRequest implements AuthenticationConvertible {

	private String providerId;
	private String accessToken;
	private String refreshToken;

	@Override
	public UsernamePasswordAuthenticationToken toAuthentication() {
		return new UsernamePasswordAuthenticationToken(this.providerId, null);
	}
}
