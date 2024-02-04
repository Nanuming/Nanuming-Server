package gdsc.nanuming.auth.service;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gdsc.nanuming.auth.dto.request.LoginRequest;
import gdsc.nanuming.auth.dto.request.RegisterRequest;
import gdsc.nanuming.auth.dto.request.ReissueRequest;
import gdsc.nanuming.auth.dto.response.LoginResponse;
import gdsc.nanuming.auth.dto.response.RegisterResponse;
import gdsc.nanuming.auth.dto.response.ReissueResponse;
import gdsc.nanuming.jwt.config.JwtConfig;
import gdsc.nanuming.jwt.dto.JwtToken;
import gdsc.nanuming.jwt.entity.RefreshToken;
import gdsc.nanuming.jwt.repository.RefreshTokenRepository;
import gdsc.nanuming.jwt.util.JwtUtil;
import gdsc.nanuming.member.entity.Member;
import gdsc.nanuming.member.repository.MemberRepository;
import gdsc.nanuming.security.util.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

	private final AuthenticationManagerBuilder authenticationManagerBuilder;
	private final MemberRepository memberRepository;
	private final JwtUtil jwtUtil;
	private final JwtConfig jwtConfig;
	private final RefreshTokenRepository refreshTokenRepository;

	@Transactional
	public RegisterResponse register(RegisterRequest registerRequest) {

		String providerId = registerRequest.getProviderId();

		if (memberRepository.existsByProviderId(providerId)) {
			// TODO: create ErrorResponse here
			throw new RuntimeException("This user is already registered.");

		}
		Member member = memberRepository.save(registerRequest.toMember());

		UsernamePasswordAuthenticationToken authenticationToken = registerRequest.toAuthentication();

		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

		CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();

		JwtToken jwtToken = jwtUtil.generateToken(authentication);

		// TODO: need refactor - extract method RefreshToken building
		RefreshToken refreshToken = RefreshToken.of(
			userDetails.getUsername(),
			jwtToken.getRefreshToken(),
			jwtConfig.getRefreshTokenPeriod());

		refreshTokenRepository.save(refreshToken);

		return RegisterResponse.of(userDetails.getUsername(), userDetails.getNickname(), jwtToken);
	}

	@Transactional
	public LoginResponse login(LoginRequest loginRequest) {

		UsernamePasswordAuthenticationToken authenticationToken = loginRequest.toAuthentication();

		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

		CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();

		JwtToken jwtToken = jwtUtil.generateToken(authentication);

		RefreshToken refreshToken = RefreshToken.of(
			userDetails.getUsername(),
			jwtToken.getRefreshToken(),
			jwtConfig.getRefreshTokenPeriod());

		refreshTokenRepository.save(refreshToken);

		return LoginResponse.of(userDetails.getUsername(), userDetails.getNickname(), jwtToken);
	}

	// TODO: refresh token reissuing method
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
		RefreshToken storedRefreshToken = refreshTokenRepository.findById(providerIdFromAccessToken)
			.orElseThrow(() -> new RuntimeException("The saved Refresh Token could not be found."));

		if (!storedRefreshToken.getProviderId().equals(providerIdFromAccessToken)) {
			throw new RuntimeException("User information from token does not match.");
		}

		Authentication authentication = jwtUtil.getAuthentication(accessToken);
		CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();
		JwtToken jwtToken = jwtUtil.generateToken(authentication);

		RefreshToken newRefreshToken = storedRefreshToken.updateRefreshToken(jwtToken.getRefreshToken());
		refreshTokenRepository.save(newRefreshToken);

		return ReissueResponse.of(userDetails.getUsername(), userDetails.getNickname(), jwtToken);
	}
}
