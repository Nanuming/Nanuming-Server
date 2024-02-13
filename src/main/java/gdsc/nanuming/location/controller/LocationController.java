package gdsc.nanuming.location.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gdsc.nanuming.common.code.CommonCode;
import gdsc.nanuming.common.response.BaseResponseWithData;
import gdsc.nanuming.location.dto.request.ShowNearLocationListRequest;
import gdsc.nanuming.location.dto.request.ShowNearLocationListRequestWithDistanceMeter;
import gdsc.nanuming.location.dto.response.ShowNearLocationListResponse;
import gdsc.nanuming.location.service.LocationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/location")
public class LocationController {

	private final LocationService locationService;

	@PostMapping
	public BaseResponseWithData<ShowNearLocationListResponse> showNearLocationList(
		@RequestBody ShowNearLocationListRequest showNearLocationListRequest) {
		log.info(">>> LocationController showNearLocationList()");
		return BaseResponseWithData.of(CommonCode.RESPONSE_SUCCESS,
			locationService.showNearLocationListAndItemCount(showNearLocationListRequest));
	}

	@PostMapping("/distance")
	public BaseResponseWithData<ShowNearLocationListResponse> showNearLocationListWithDistanceMeter(
		@RequestBody ShowNearLocationListRequestWithDistanceMeter showNearLocationListRequestWithDistanceMeter) {
		log.info(">>> LocationController showNearLocationListWithDistanceMeter()");
		return BaseResponseWithData.of(CommonCode.RESPONSE_SUCCESS,
			locationService.showNearLocationListWithDistanceMeterAndItemCount(
				showNearLocationListRequestWithDistanceMeter));
	}

}
