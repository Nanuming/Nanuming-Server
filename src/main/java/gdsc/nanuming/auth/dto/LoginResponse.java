package gdsc.nanuming.auth.dto;

import gdsc.nanuming.jwt.dto.JwtToken;
import lombok.Getter;

@Getter
public class LoginResponse {

	private JwtToken token;

}
