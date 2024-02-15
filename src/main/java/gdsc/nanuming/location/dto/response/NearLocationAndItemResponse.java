package gdsc.nanuming.location.dto.response;

import java.util.List;

import gdsc.nanuming.item.dto.ItemOutlineDto;
import gdsc.nanuming.location.dto.LocationInfoDto;
import lombok.Builder;
import lombok.Getter;

@Getter
public class NearLocationAndItemResponse {

	List<LocationInfoDto> locationInfoDtoList;
	List<ItemOutlineDto> itemOutlineDtoList;

	@Builder
	private NearLocationAndItemResponse(List<LocationInfoDto> locationInfoDtoList,
		List<ItemOutlineDto> itemOutlineDtoList) {
		this.locationInfoDtoList = locationInfoDtoList;
		this.itemOutlineDtoList = itemOutlineDtoList;
	}

	public static NearLocationAndItemResponse of(List<LocationInfoDto> locationInfoDtoList,
		List<ItemOutlineDto> itemOutlineDtoList) {

		return NearLocationAndItemResponse.builder()
			.locationInfoDtoList(locationInfoDtoList)
			.itemOutlineDtoList(itemOutlineDtoList)
			.build();
	}
}
