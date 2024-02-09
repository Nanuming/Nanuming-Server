package gdsc.nanuming.item.dto.response;

import java.util.List;

import gdsc.nanuming.item.dto.ItemOutlineDto;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ShowItemListResponse {

	private List<ItemOutlineDto> itemOutlineDtoList;

	@Builder
	private ShowItemListResponse(List<ItemOutlineDto> itemOutlineDtoList) {
		this.itemOutlineDtoList = itemOutlineDtoList;
	}

	public static ShowItemListResponse from(List<ItemOutlineDto> itemOutlineDtoList) {
		return ShowItemListResponse.builder()
			.itemOutlineDtoList(itemOutlineDtoList)
			.build();
	}
}
