package gdsc.nanuming.reservation.controller;

import static gdsc.nanuming.common.code.CommonCode.*;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gdsc.nanuming.common.response.BaseResponseWithData;
import gdsc.nanuming.reservation.dto.request.ReservationRequest;
import gdsc.nanuming.reservation.dto.response.ReservationResponse;
import gdsc.nanuming.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reservation")
public class ReservationController {

	private final ReservationService reservationService;

	@PostMapping
	public BaseResponseWithData<ReservationResponse> reserveItem(@RequestBody ReservationRequest request) {
		log.info(">>> ReservationController reserveItem()");
		return BaseResponseWithData.of(RESPONSE_SUCCESS, reservationService.reserveLocker(request));
	}
}
