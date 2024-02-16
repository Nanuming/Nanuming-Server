package gdsc.nanuming.location.dto.response;

import java.util.List;

import gdsc.nanuming.item.dto.ItemOutlineDto;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SpecificLocationItemListResponse {

	private List<ItemOutlineDto> itemOutlineDtoList;

	@Builder
	private SpecificLocationItemListResponse(List<ItemOutlineDto> itemOutlineDtoList) {
		this.itemOutlineDtoList = itemOutlineDtoList;
	}

	public static SpecificLocationItemListResponse from(List<ItemOutlineDto> itemOutlineDtoList) {
		return SpecificLocationItemListResponse.builder()
			.itemOutlineDtoList(itemOutlineDtoList)
			.build();
	}
}
