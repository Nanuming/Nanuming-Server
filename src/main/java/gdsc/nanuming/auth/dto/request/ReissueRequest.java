package gdsc.nanuming.auth.dto.request;

import lombok.Getter;

@Getter
public class ReissueRequest {

	private String accessToken;
	private String refreshToken;

}
