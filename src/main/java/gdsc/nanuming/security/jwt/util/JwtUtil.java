package gdsc.nanuming.security.jwt.util;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import com.google.api.client.util.Value;

import gdsc.nanuming.security.jwt.dto.GeneratedToken;
import gdsc.nanuming.member.MemberRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtUtil {

	@Value("${sm://JWT_SECRET}")
	private String secret;

	@Value("${sm://JWT_ACCESS_TOKEN_PERIOD}")
	private Long accessTokenPeriod;

	@Value("${sm://JWT_REFRESH_TOKEN_PERIOD}")
	private long refreshTokenPeriod;

	private SecretKey secretKey;

	@PostConstruct
	protected void init() {
		log.info(">>> JwtUtil secret: {}", secret);
		secretKey = Keys.hmacShaKeyFor(secret.getBytes());
		log.info(">>> JwtUtil secretKey: {}", secretKey);
	}

	public GeneratedToken generateToken(String email, MemberRole role) {

		String accessToken = generateAccessToken(email, role);
		String refreshToken = generateRefreshToken(email, role);

		return new GeneratedToken();
		// TODO: need additional work here
	}

	private String generateAccessToken(String email, MemberRole role) {
		Claims claims = Jwts.claims().setSubject(email);
		claims.put("role", role.getValue());

		Date now = new Date();

		return
			Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(now)
				.setExpiration(new Date(now.getTime() + accessTokenPeriod))
				.signWith(secretKey)
				.compact();
	}

	private String generateRefreshToken(String email, MemberRole role) {
		Claims claims = Jwts.claims().setSubject(email);
		claims.put("role", role.getValue());

		Date now = new Date();

		return
			Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(now)
				.setExpiration(new Date(now.getTime() + refreshTokenPeriod))
				.signWith(secretKey)
				.compact();
	}

	public boolean verifyToken(String token) {
		try {
			Jws<Claims> claims = Jwts.parserBuilder()
				.setSigningKey(secretKey)
				.build().parseClaimsJws(token);

			return claims.getBody()
				.getExpiration()
				.after(new Date());
		} catch (Exception e) {
			return false;
		}
	}
}
