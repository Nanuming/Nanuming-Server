package gdsc.nanuming.item.dto.response;

public class AddItemResponse {

	private Long id;

	private AddItemResponse(Long id) {
		this.id = id;
	}

	public static AddItemResponse from(Long id) {
		return AddItemResponse.builder()
			.id(id)
			.build();
	}
}
