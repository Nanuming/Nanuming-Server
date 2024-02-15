package gdsc.nanuming.locker.controller;

import static gdsc.nanuming.common.code.CommonCode.*;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gdsc.nanuming.common.response.BaseResponseWithData;
import gdsc.nanuming.locker.dto.request.OpenLockerRequest;
import gdsc.nanuming.locker.dto.response.OpenLockerResponse;
import gdsc.nanuming.locker.service.LockerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/locker")
public class LockerController {

	private final LockerService lockerService;

	@PostMapping
	public BaseResponseWithData<OpenLockerResponse> openLocker(@RequestBody OpenLockerRequest request) {
		log.info(">>> LockerController openLocker()");
		return BaseResponseWithData.of(RESPONSE_SUCCESS, lockerService.openLocker(request));
	}
}
