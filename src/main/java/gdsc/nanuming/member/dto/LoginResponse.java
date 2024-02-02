package gdsc.nanuming.member.dto;

import gdsc.nanuming.security.jwt.dto.GeneratedToken;
import lombok.Getter;

@Getter
public class LoginResponse {

	private GeneratedToken token;

}
