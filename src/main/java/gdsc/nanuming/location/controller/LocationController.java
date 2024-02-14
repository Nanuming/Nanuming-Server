package gdsc.nanuming.location.controller;

import static gdsc.nanuming.common.code.CommonCode.*;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gdsc.nanuming.common.response.BaseResponseWithData;
import gdsc.nanuming.location.dto.request.NearLocationAndItemRequest;
import gdsc.nanuming.location.dto.response.NearLocationAndItemResponse;
import gdsc.nanuming.location.service.LocationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/location")
public class LocationController {

	private final LocationService locationService;

	@PostMapping("/nearBy")
	public BaseResponseWithData<NearLocationAndItemResponse> showNearLocationAndItem(
		@RequestBody NearLocationAndItemRequest request) {
		log.info(">>> Run LocationController showNearLocationAndItem()");
		return BaseResponseWithData.of(RESPONSE_SUCCESS, locationService.getNearLocationAndItemList(request));
	}

}
