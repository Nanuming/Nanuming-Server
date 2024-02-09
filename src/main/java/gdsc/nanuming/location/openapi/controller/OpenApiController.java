package gdsc.nanuming.location.openapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gdsc.nanuming.common.code.CommonCode;
import gdsc.nanuming.common.response.BaseResponse;
import gdsc.nanuming.location.openapi.service.OpenApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/openapi")
public class OpenApiController {

	private final OpenApiService openApiService;

	@GetMapping("/childCareInfo/load")
	public BaseResponse callChildCareInfoApi() {
		openApiService.callChildCareInfoApi();
		return BaseResponse.from(CommonCode.RESPONSE_SUCCESS);
	}
}
