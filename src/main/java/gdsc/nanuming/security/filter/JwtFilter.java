package gdsc.nanuming.security.filter;

import gdsc.nanuming.security.jwt.util.JwtUtil;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtFilter {

	public static final String AUTHORIZATION_HEADER = "Authorization";
	public static final String BEARER_PREFIX = "Bearer ";

	private final JwtUtil jwtUtil;
}
