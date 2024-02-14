package gdsc.nanuming.location.dto.response;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
public class NearLocationIdListResponse {

	private List<Long> locationIdList;

	@Builder
	private NearLocationIdListResponse(List<Long> locationIdList) {
		this.locationIdList = locationIdList;
	}

	public static NearLocationIdListResponse from(List<Long> locationIdList) {
		return NearLocationIdListResponse.builder()
			.locationIdList(locationIdList)
			.build();
	}
}
