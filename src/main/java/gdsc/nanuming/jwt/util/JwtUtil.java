package gdsc.nanuming.jwt.util;

import java.util.Base64;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import com.google.api.client.util.Value;

import gdsc.nanuming.jwt.dto.GeneratedToken;
import gdsc.nanuming.member.MemberRole;
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
		secretKey = Base64.getEncoder().encodeToString(secret.getBytes());
		log.info(">>> JwtUtil secret: {}", secret);
		log.info(">>> JwtUtil secretKey: {}", secretKey);
	}

	public GeneratedToken generateToken(String email, MemberRole role) {

	}



}
