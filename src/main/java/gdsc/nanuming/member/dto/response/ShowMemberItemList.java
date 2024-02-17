package gdsc.nanuming.member.dto.response;

import java.util.List;

import gdsc.nanuming.item.dto.ItemOutlineDto;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ShowMemberItemList {

	private List<ItemOutlineDto> memberItemOutlineDtoList;

	@Builder
	private ShowMemberItemList(List<ItemOutlineDto> memberItemOutlineDtoList) {
		this.memberItemOutlineDtoList = memberItemOutlineDtoList;
	}

	public static ShowMemberItemList from(List<ItemOutlineDto> memberItemOutlineDtoList) {
		return ShowMemberItemList.builder()
			.memberItemOutlineDtoList(memberItemOutlineDtoList)
			.build();
	}
}
