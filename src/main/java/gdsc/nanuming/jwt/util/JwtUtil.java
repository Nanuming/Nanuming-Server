package gdsc.nanuming.jwt.util;

import java.util.Base64;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.google.api.client.util.Value;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtUtil {

	@Value("${sm://JWT_SECRET}")
	private String secret;
	private String secretKey;

	@PostConstruct
	protected void init() {
		secretKey = Base64.getEncoder().encodeToString(secret.getBytes());
		log.info(">>> JwtUtil secret: {}", secret);
		log.info(">>> JwtUtil secretKey: {}", secretKey);
	}


}
