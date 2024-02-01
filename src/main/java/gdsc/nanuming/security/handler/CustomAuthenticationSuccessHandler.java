// package gdsc.nanuming.security.handler;
//
// import static gdsc.nanuming.common.code.CommonCode.*;
//
// import java.io.IOException;
//
// import org.apache.http.entity.ContentType;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.oauth2.core.oidc.user.OidcUser;
// import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
// import org.springframework.stereotype.Component;
//
// import com.fasterxml.jackson.databind.ObjectMapper;
//
// import gdsc.nanuming.common.response.BaseResponseWithData;
// import gdsc.nanuming.security.dto.GeneratedToken;
// import gdsc.nanuming.security.dto.RegisterNotice;
// import gdsc.nanuming.security.jwt.util.JwtUtil;
// import jakarta.servlet.ServletException;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;
// import lombok.RequiredArgsConstructor;
// import lombok.extern.slf4j.Slf4j;
//
// @Slf4j
// @Component
// @RequiredArgsConstructor
// public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
//
// 	private final JwtUtil jwtUtil;
// 	private final ObjectMapper objectMapper;
//
// 	@Override
// 	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
// 		Authentication authentication) throws IOException, ServletException {
//
// 		OidcUser oidcUser = (OidcUser)authentication.getPrincipal();
//
// 		String email = oidcUser.getAttribute("email");
// 		String providerId = oidcUser.getAttribute("providerId");
// 		boolean registered = oidcUser.getAttribute("registered");
// 		String role = oidcUser.getAuthorities()
// 			.stream().findFirst().orElseThrow(IllegalAccessError::new)
// 			.getAuthority();
// 		log.info(">>> CustomAuthenticationSuccessHandler email: {}", email);
// 		log.info(">>> CustomAuthenticationSuccessHandler providerId: {}", providerId);
// 		log.info(">>> CustomAuthenticationSuccessHandler registered: {}", registered);
// 		log.info(">>> CustomAuthenticationSuccessHandler role: {}", role);
//
// 		if (registered) {
// 			GeneratedToken token = jwtUtil.generateToken(email, role);
// 			log.info(">>> CustomAuthenticationSuccessHandler accessToken: {}", token.getAccessToken());
// 			log.info(">>> CustomAuthenticationSuccessHandler refreshToken: {}", token.getRefreshToken());
//
// 			// TODO: 정상 동작 확인 후 제거할 주석
// 			// Map<String, Object> tokenData = new HashMap<>();
// 			// tokenData.put("accessToken", token.getAccessToken());
// 			// tokenData.put("refreshToken", token.getRefreshToken());
//
// 			BaseResponseWithData<GeneratedToken> responseBody = BaseResponseWithData.of(
// 				TOKEN_SUCCESS, token);
//
// 			// JSON
// 			// TODO: Refactoring necessary
// 			response.setContentType(String.valueOf(ContentType.APPLICATION_JSON));
// 			response.setCharacterEncoding("UTF-8");
// 			response.getWriter().write(objectMapper.writeValueAsString(responseBody));
// 			response.getWriter().flush();
//
// 		} else {
//
// 			BaseResponseWithData<RegisterNotice> responseBody = BaseResponseWithData.of(NEED_REGISTER,
// 				RegisterNotice.from(false));
//
// 			// JSON
// 			// TODO: Refactoring necessary
// 			response.setContentType(String.valueOf(ContentType.APPLICATION_JSON));
// 			response.setCharacterEncoding("UTF-8");
// 			response.getWriter().write(objectMapper.writeValueAsString(responseBody));
// 			response.getWriter().flush();
//
// 		}
// 	}
// }
