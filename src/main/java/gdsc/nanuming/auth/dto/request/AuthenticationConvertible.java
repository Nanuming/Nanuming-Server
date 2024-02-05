package gdsc.nanuming.auth.dto.request;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public interface AuthenticationConvertible {

	UsernamePasswordAuthenticationToken toAuthentication();

}
