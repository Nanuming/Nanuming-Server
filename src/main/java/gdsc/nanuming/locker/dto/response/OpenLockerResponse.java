package gdsc.nanuming.locker.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OpenLockerResponse {

	private Long lockerId;

	@Builder
	private OpenLockerResponse(Long lockerId) {
		this.lockerId = lockerId;
	}

	public static OpenLockerResponse from(Long lockerId) {
		return OpenLockerResponse.builder()
			.lockerId(lockerId)
			.build();
	}
}
