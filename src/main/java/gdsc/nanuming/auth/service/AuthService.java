package gdsc.nanuming.auth.service;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gdsc.nanuming.auth.dto.request.LoginRequest;
import gdsc.nanuming.auth.dto.request.RegisterRequest;
import gdsc.nanuming.auth.dto.response.LoginResponse;
import gdsc.nanuming.auth.dto.response.RegisterResponse;
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
@Transactional(readOnly = true)
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
			throw new RuntimeException("이미 가입되어 있는 유저입니다.");
		}
		Member member = memberRepository.save(registerRequest.toMember());

		UsernamePasswordAuthenticationToken authenticationToken = registerRequest.toAuthentication();

		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

		JwtToken jwtToken = jwtUtil.generateToken(authentication);

		// TODO: need refactor - extract meethod RefreshToken building
		RefreshToken refreshToken = RefreshToken.of(
			jwtToken.getRefreshToken(),
			member.getProviderId(),
			jwtConfig.getRefreshTokenPeriod());
		log.info(">>> AuthService register() refreshToken: {}", refreshToken);

		refreshTokenRepository.save(refreshToken);

		// TODO: modify RegisterResponse like LoginResponse - using userDetails
		return RegisterResponse.of(member, jwtToken);
	}

	@Transactional
	public LoginResponse login(LoginRequest loginRequest) {
		UsernamePasswordAuthenticationToken authenticationToken = loginRequest.toAuthentication();

		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

		CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();

		JwtToken jwtToken = jwtUtil.generateToken(authentication);

		RefreshToken refreshToken = RefreshToken.of(
			jwtToken.getRefreshToken(),
			userDetails.getUsername(),
			jwtConfig.getRefreshTokenPeriod());

		refreshTokenRepository.save(refreshToken);

		return LoginResponse.of(userDetails.getUsername(), userDetails.getNickname(), jwtToken);
	}

	// TODO: refresh token reissuing method

}
