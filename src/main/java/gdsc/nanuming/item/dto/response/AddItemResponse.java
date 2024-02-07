package gdsc.nanuming.item.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AddItemResponse {

	private Long id;

	@Builder
	private AddItemResponse(Long id) {
		this.id = id;
	}

	public static AddItemResponse from(Long id) {
		return AddItemResponse.builder()
			.id(id)
			.build();
	}
}
