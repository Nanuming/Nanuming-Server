package gdsc.nanuming.member.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ConfirmImageResponse {

	private Long confirmItemImageId;

	@Builder
	private ConfirmImageResponse(Long confirmItemImageId) {
		this.confirmItemImageId = confirmItemImageId;
	}

	public static ConfirmImageResponse from(Long confirmItemImageId) {
		return ConfirmImageResponse.builder()
			.confirmItemImageId(confirmItemImageId)
			.build();
	}
}
