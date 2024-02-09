package gdsc.nanuming.auth.dto.request;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import lombok.Getter;

@Getter
public class LoginRequest implements AuthenticationConvertible {

	private String providerId;

	@Override
	public UsernamePasswordAuthenticationToken toAuthentication() {
		return new UsernamePasswordAuthenticationToken(this.providerId, null);
	}
}
