package gdsc.nanuming.auth.dto.request;

import lombok.Getter;

@Getter
public class LogoutRequest {

	private String accessToken;
	private String refreshToken;

}
