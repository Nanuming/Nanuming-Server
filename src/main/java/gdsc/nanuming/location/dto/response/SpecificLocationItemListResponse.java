package gdsc.nanuming.location.dto.response;

import java.util.List;

import gdsc.nanuming.item.dto.ItemOutlineDto;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SpecificLocationItemListResponse {

	private String locationName;
	private int emptyLockerCount;
	private int occupiedLockerCount;
	private List<ItemOutlineDto> itemOutlineDtoList;

	@Builder
	private SpecificLocationItemListResponse(
		String locationName,
		int emptyLockerCount,
		int occupiedLockerCount,
		List<ItemOutlineDto> itemOutlineDtoList) {
		this.locationName = locationName;
		this.emptyLockerCount = emptyLockerCount;
		this.occupiedLockerCount = occupiedLockerCount;
		this.itemOutlineDtoList = itemOutlineDtoList;
	}

	public static SpecificLocationItemListResponse of(
		String locationName,
		int emptyLockerCount,
		int occupiedLockerCount,
		List<ItemOutlineDto> itemOutlineDtoList) {
		return SpecificLocationItemListResponse.builder()
			.locationName(locationName)
			.emptyLockerCount(emptyLockerCount)
			.occupiedLockerCount(occupiedLockerCount)
			.itemOutlineDtoList(itemOutlineDtoList)
			.build();
	}
}
