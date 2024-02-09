package gdsc.nanuming.auth.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LogoutResponse {

	private String providerId;

	@Builder
	private LogoutResponse(String providerId) {
		this.providerId = providerId;
	}

	public static LogoutResponse from(String providerId) {
		return LogoutResponse.builder()
			.providerId(providerId)
			.build();
	}
}
