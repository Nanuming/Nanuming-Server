package gdsc.nanuming.member.dto.response;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TemporaryItemDetailResponse {

	private Long itemId;
	private List<String> itemImageUrlList;
	private String category;
	private String locationName;
	private String nickname;
	private String description;
	private boolean isOwner;
	private String createdAt;
	private String updatedAt;

	@Builder
	private TemporaryItemDetailResponse(Long itemId, List<String> itemImageUrlList, String category,
		String locationName, String nickname, String description, boolean isOwner, String createdAt, String updatedAt) {
		this.itemId = itemId;
		this.itemImageUrlList = itemImageUrlList;
		this.category = category;
		this.locationName = locationName;
		this.nickname = nickname;
		this.description = description;
		this.isOwner = isOwner;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public static TemporaryItemDetailResponse of(Long itemId, List<String> itemImageUrlList, String category,
		String locationName, String nickname, String description, boolean isOwner, String createdAt, String updatedAt) {
		return TemporaryItemDetailResponse.builder()
			.itemId(itemId)
			.itemImageUrlList(itemImageUrlList)
			.category(category)
			.locationName(locationName)
			.nickname(nickname)
			.description(description)
			.isOwner(isOwner)
			.createdAt(createdAt)
			.updatedAt(updatedAt)
			.build();
	}
}
