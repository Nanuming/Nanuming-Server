package gdsc.nanuming.item.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AssignLockerResponse {

	private Long itemId;
	private Long lockerId;

	@Builder
	private AssignLockerResponse(Long itemId, Long lockerId) {
		this.itemId = itemId;
		this.lockerId = lockerId;
	}

	public static AssignLockerResponse of(Long itemId, Long lockerId) {
		return AssignLockerResponse.builder()
			.itemId(itemId)
			.lockerId(lockerId)
			.build();
	}
}
