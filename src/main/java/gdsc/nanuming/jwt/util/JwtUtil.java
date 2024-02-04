package gdsc.nanuming.jwt.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import gdsc.nanuming.jwt.config.JwtConfig;
import gdsc.nanuming.jwt.dto.JwtToken;
import gdsc.nanuming.security.util.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtUtil {

	private final JwtConfig jwtConfig;

	private static final String AUTHORITIES_KEY = "auth";

	private SecretKey secretKey;

	@PostConstruct
	protected void init() {
		log.info(">>> JwtUtil init()");
		secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
		log.info(">>> JwtUtil init() secretKey: {}", secretKey);
	}

	public JwtToken generateToken(Authentication authentication) {

		String accessToken = generateAccessToken(authentication);
		String refreshToken = generateRefreshToken();

		return JwtToken.of(accessToken, refreshToken);
	}

	private String generateAccessToken(Authentication authentication) {

		String authorities = getAuthorities(authentication);
		CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();
		Date currentTime = new Date();
		Long accessTokenPeriod = jwtConfig.getAccessTokenPeriod();
		Date tokenExpirationTime = getTokenExpirationTime(currentTime, accessTokenPeriod);

		String email = userDetails.getEmail();
		String nickname = userDetails.getNickname();

		return
			Jwts.builder()
				.setSubject(authentication.getName())
				.claim(AUTHORITIES_KEY, authorities)
				.claim("email", email)
				.claim("nickname", nickname)
				.setIssuedAt(currentTime)
				.setExpiration(tokenExpirationTime)
				.signWith(secretKey)
				.compact();
	}

	private String generateRefreshToken() {

		Date currentTime = new Date();
		Long refreshTokenPeriod = jwtConfig.getRefreshTokenPeriod();
		Date tokenExpirationTime = getTokenExpirationTime(currentTime, refreshTokenPeriod);

		return
			Jwts.builder()
				.setIssuedAt(currentTime)
				.setExpiration(tokenExpirationTime)
				.signWith(secretKey)
				.compact();
	}

	public Authentication getAuthentication(String token) {
		// token decryption
		Claims claims = parseClaims(token);

		// TODO: need custom exception processing
		if (claims.get(AUTHORITIES_KEY) == null) {
			throw new RuntimeException("권한 정보가 없는 토큰입니다.");
		}

		// get authorities from claims
		Collection<? extends GrantedAuthority> authorities =
			Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
				.map(SimpleGrantedAuthority::new)
				.toList();

		CustomUserDetails userDetails = CustomUserDetails.of(
			claims.getSubject(),
			(String)claims.get("email"),
			(String)claims.get("nickname"),
			authorities
		);

		return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
	}

	private Claims parseClaims(String token) {
		try {
			return Jwts.parserBuilder()
				.setSigningKey(secretKey)
				.build().parseClaimsJws(token)
				.getBody();
		} catch (ExpiredJwtException e) {
			log.warn(e.getMessage());
			return e.getClaims();
		}
	}

	// TODO: create custom exceptions here after adapting GlobalExceptionHandler
	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
			return true;
		} catch (MalformedJwtException e) {
			log.warn("잘못된 JWT 서명입니다.");
			throw e;
		} catch (ExpiredJwtException e) {
			log.warn("만료된 JWT 토큰입니다.");
			throw e;
		} catch (UnsupportedJwtException e) {
			log.warn("지원되지 않는 JWT 토큰입니다.");
			throw e;
		} catch (IllegalArgumentException e) {
			log.warn("JWT 토큰이 잘못되었습니다.");
			throw e;
		}
	}

	private String getAuthorities(Authentication authentication) {
		return authentication.getAuthorities().stream()
			.map(GrantedAuthority::getAuthority)
			.collect(Collectors.joining(","));
	}

	private Date getTokenExpirationTime(Date currentTime, Long tokenPeriod) {
		return new Date(currentTime.getTime() + tokenPeriod);
	}

}
