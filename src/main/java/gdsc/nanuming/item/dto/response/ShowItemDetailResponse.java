package gdsc.nanuming.item.dto.response;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ShowItemDetailResponse {

	private Long itemId;
	private List<String> itemImageUrlList;
	private String category;
	private String nickname;
	private String location;
	private String description;
	private boolean isOwner;
	private String createdAt;
	private String updatedAt;

	@Builder
	private ShowItemDetailResponse(Long itemId, List<String> itemImageUrlList, String category, String nickname,
		String location, String description, boolean isOwner, String createdAt, String updatedAt) {
		this.itemId = itemId;
		this.itemImageUrlList = itemImageUrlList;
		this.category = category;
		this.nickname = nickname;
		this.location = location;
		this.description = description;
		this.isOwner = isOwner;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public static ShowItemDetailResponse of(Long itemId, List<String> itemImageUrlList, String category,
		String nickname, String location, String description, boolean isOwner, String createdAt, String updatedAt) {
		return ShowItemDetailResponse.builder()
			.itemId(itemId)
			.itemImageUrlList(itemImageUrlList)
			.category(category)
			.nickname(nickname)
			.location(location)
			.description(description)
			.isOwner(isOwner)
			.createdAt(createdAt)
			.updatedAt(updatedAt)
			.build();
	}
}
