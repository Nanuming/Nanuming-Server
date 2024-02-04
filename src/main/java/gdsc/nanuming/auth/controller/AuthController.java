package gdsc.nanuming.auth.controller;

import static gdsc.nanuming.common.code.CommonCode.*;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gdsc.nanuming.auth.dto.request.LoginRequest;
import gdsc.nanuming.auth.dto.request.RegisterRequest;
import gdsc.nanuming.auth.dto.request.ReissueRequest;
import gdsc.nanuming.auth.dto.response.LoginResponse;
import gdsc.nanuming.auth.dto.response.RegisterResponse;
import gdsc.nanuming.auth.dto.response.ReissueResponse;
import gdsc.nanuming.auth.service.AuthService;
import gdsc.nanuming.common.response.BaseResponseWithData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	@PostMapping("/register")
	public BaseResponseWithData<RegisterResponse> register(@RequestBody RegisterRequest registerRequest) {
		log.info(">>> AuthController register()");
		return BaseResponseWithData.of(RESPONSE_SUCCESS, authService.register(registerRequest));
	}

	@PostMapping("/login")
	public BaseResponseWithData<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
		log.info(">>> AuthController login()");
		return BaseResponseWithData.of(RESPONSE_SUCCESS, authService.login(loginRequest));
	}

	@PostMapping("/reissue")
	public BaseResponseWithData<ReissueResponse> reissue(@RequestBody ReissueRequest reissueRequest) {
		log.info(">>> AuthController reissue()");
		return BaseResponseWithData.of(RESPONSE_SUCCESS, authService.reissue(reissueRequest));
	}
}
