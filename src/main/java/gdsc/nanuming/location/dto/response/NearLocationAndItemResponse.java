package gdsc.nanuming.location.dto.response;

import java.util.List;

import gdsc.nanuming.location.dto.LocationWithItemOutlineListDto;
import lombok.Builder;
import lombok.Getter;

@Getter
public class NearLocationAndItemResponse {

	List<LocationWithItemOutlineListDto> locationWithItemOutline;

	@Builder
	private NearLocationAndItemResponse(List<LocationWithItemOutlineListDto> locationWithItemOutline) {
		this.locationWithItemOutline = locationWithItemOutline;
	}

	public static NearLocationAndItemResponse from(List<LocationWithItemOutlineListDto> locationWithItemOutline) {
		return NearLocationAndItemResponse.builder()
			.locationWithItemOutline(locationWithItemOutline)
			.build();
	}
}
