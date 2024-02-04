package gdsc.nanuming.security.filter;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import gdsc.nanuming.jwt.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

	public static final String AUTHORIZATION_HEADER = "Authorization";
	public static final String PREFIX = "Bearer ";
	public static final int PREFIX_LENGTH = PREFIX.length();

	private final JwtUtil jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {

		log.info(">>> JwtFilter doFilterInternal()");

		String jwt = extractTokenFromHeader(request);

		log.info(">>> JwtFilter doFilterInternal() jwt: {}", jwt);

		if (StringUtils.hasText(jwt) && jwtUtil.validateToken(jwt)) {
			Authentication authentication = jwtUtil.getAuthentication(jwt);
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}

		filterChain.doFilter(request, response);
	}

	private String extractTokenFromHeader(HttpServletRequest request) {

		String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(PREFIX)) {
			return bearerToken.substring(PREFIX_LENGTH).trim();
		}
		return null;
	}
}
