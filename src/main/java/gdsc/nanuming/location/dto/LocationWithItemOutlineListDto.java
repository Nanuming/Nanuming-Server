package gdsc.nanuming.location.dto;

import java.util.List;

import gdsc.nanuming.item.dto.ItemOutlineDto;
import lombok.Builder;
import lombok.Getter;

@Getter
public class LocationWithItemOutlineListDto {

	private Long locationId;
	private double latitude;
	private double longitude;
	private List<ItemOutlineDto> itemOutlineDtoList;

	@Builder
	private LocationWithItemOutlineListDto(Long locationId, double latitude, double longitude,
		List<ItemOutlineDto> itemOutlineDtoList) {
		this.locationId = locationId;
		this.latitude = latitude;
		this.longitude = longitude;
		this.itemOutlineDtoList = itemOutlineDtoList;
	}

	public static LocationWithItemOutlineListDto of(Long locationId, double latitude, double longitude,
		List<ItemOutlineDto> itemOutlineDtoList) {
		return LocationWithItemOutlineListDto.builder()
			.locationId(locationId)
			.latitude(latitude)
			.longitude(longitude)
			.itemOutlineDtoList(itemOutlineDtoList)
			.build();
	}
}
