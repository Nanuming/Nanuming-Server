package gdsc.nanuming.auth.service;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gdsc.nanuming.auth.dto.request.AuthenticationConvertible;
import gdsc.nanuming.auth.dto.request.LoginRequest;
import gdsc.nanuming.auth.dto.request.LogoutRequest;
import gdsc.nanuming.auth.dto.request.RegisterRequest;
import gdsc.nanuming.auth.dto.request.ReissueRequest;
import gdsc.nanuming.auth.dto.response.LoginResponse;
import gdsc.nanuming.auth.dto.response.LogoutResponse;
import gdsc.nanuming.auth.dto.response.RegisterResponse;
import gdsc.nanuming.auth.dto.response.ReissueResponse;
import gdsc.nanuming.jwt.dto.JwtToken;
import gdsc.nanuming.jwt.util.JwtUtil;
import gdsc.nanuming.member.repository.MemberRepository;
import gdsc.nanuming.redis.entity.BlacklistToken;
import gdsc.nanuming.redis.entity.RefreshToken;
import gdsc.nanuming.redis.repository.BlacklistTokenRepository;
import gdsc.nanuming.redis.repository.RefreshTokenRepository;
import gdsc.nanuming.security.util.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

	private final JwtUtil jwtUtil;
	private final MemberRepository memberRepository;
	private final RefreshTokenRepository refreshTokenRepository;
	private final BlacklistTokenRepository blacklistTokenRepository;
	private final AuthenticationManagerBuilder authenticationManagerBuilder;

	private static final String LOGOUT = "logout";

	@Transactional
	public RegisterResponse register(RegisterRequest registerRequest) {

		String providerId = registerRequest.getProviderId();

		if (memberRepository.existsByProviderId(providerId)) {
			// TODO: create ErrorResponse here
			throw new RuntimeException("This user is already registered.");

		}
		memberRepository.save(registerRequest.toMember());

		CustomUserDetails userDetails = getUserDetails(registerRequest);
		JwtToken jwtToken = jwtUtil.generateToken(userDetails);

		saveRefreshToken(userDetails, jwtToken);

		return RegisterResponse.of(userDetails.getUsername(), userDetails.getNickname(), jwtToken);
	}

	@Transactional
	public LoginResponse login(LoginRequest loginRequest) {

		CustomUserDetails userDetails = getUserDetails(loginRequest);
		JwtToken jwtToken = jwtUtil.generateToken(userDetails);

		saveRefreshToken(userDetails, jwtToken);

		return LoginResponse.of(userDetails.getUsername(), userDetails.getNickname(), jwtToken);
	}

	@Transactional
	public LogoutResponse logout(LogoutRequest logoutRequest) {

		String accessToken = logoutRequest.getAccessToken();
		if (!jwtUtil.validateToken(accessToken)) {
			throw new RuntimeException("Access Token is invalid.");
		}

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		log.info(">>> AuthService logout() authentication: {}", authentication);
		log.info(">>> AuthService logout() authentication.getName(): {}", authentication.getName());

		if (!authentication.isAuthenticated()) {
			throw new RuntimeException("User is not authenticated");
		}

		if (refreshTokenRepository.findByProviderId(authentication.getName()).isPresent()) {
			refreshTokenRepository.delete(authentication.getName());
		}

		saveBlacklistToken(accessToken);

		return LogoutResponse.from(authentication.getName());
	}

	@Transactional
	public ReissueResponse reissue(ReissueRequest reissueRequest) {

		String accessToken = reissueRequest.getAccessToken();
		String refreshToken = reissueRequest.getRefreshToken();
		String providerIdFromAccessToken = jwtUtil.getProviderId(accessToken);

		// TODO: need custom exception processing - login required
		if (!jwtUtil.validateToken(refreshToken)) {
			throw new RuntimeException("Refresh Token is invalid.");
		}

		// TODO: need custom exception processing
		RefreshToken storedRefreshToken = refreshTokenRepository.findByProviderId(providerIdFromAccessToken)
			.orElseThrow(() -> new RuntimeException("The saved Refresh Token could not be found."));

		if (!storedRefreshToken.getProviderId().equals(providerIdFromAccessToken)) {
			throw new RuntimeException("User information from token does not match.");
		}

		CustomUserDetails userDetails = getUserDetails(reissueRequest);
		JwtToken jwtToken = jwtUtil.generateToken(userDetails);

		saveRefreshToken(userDetails, jwtToken);

		return ReissueResponse.of(userDetails.getUsername(), userDetails.getNickname(), jwtToken);
	}

	private CustomUserDetails getUserDetails(AuthenticationConvertible request) {
		UsernamePasswordAuthenticationToken authenticationToken = request.toAuthentication();
		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
		return (CustomUserDetails)authentication.getPrincipal();
	}

	private void saveRefreshToken(CustomUserDetails userDetails, JwtToken jwtToken) {
		RefreshToken refreshToken = RefreshToken.of(
			userDetails.getUsername(),
			jwtToken.getRefreshToken());

		refreshTokenRepository.save(refreshToken);
	}

	private void saveBlacklistToken(String accessToken) {
		long remainingExpirationTime = jwtUtil.getRemainingExpirationTime(accessToken);
		BlacklistToken blacklistToken = BlacklistToken.of(
			accessToken,
			LOGOUT
		);

		blacklistTokenRepository.save(blacklistToken, remainingExpirationTime);
	}
}
