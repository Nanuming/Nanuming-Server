package gdsc.nanuming.item.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ItemOutlineDto {

	private Long itemId;
	private String mainItemImageUrl;
	private String title;
	private String locationName;
	private String categoryName;

	@Builder
	private ItemOutlineDto(Long itemId, String mainItemImageUrl, String title, String locationName,
		String categoryName) {
		this.itemId = itemId;
		this.mainItemImageUrl = mainItemImageUrl;
		this.title = title;
		this.locationName = locationName;
		this.categoryName = categoryName;
	}

	public static ItemOutlineDto of(Long itemId, String mainItemImageUrl, String title, String locationName,
		String categoryName) {
		return ItemOutlineDto.builder()
			.itemId(itemId)
			.mainItemImageUrl(mainItemImageUrl)
			.title(title)
			.locationName(locationName)
			.categoryName(categoryName)
			.build();
	}

}
