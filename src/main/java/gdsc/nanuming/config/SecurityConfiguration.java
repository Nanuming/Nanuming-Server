package gdsc.nanuming.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import gdsc.nanuming.security.handler.CustomAuthenticationSuccessHandler;
import gdsc.nanuming.security.service.CustomOidcUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

	private final CustomOidcUserService customOidcUserService;
	private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		// activate CORS
		http.cors(cors -> cors.configurationSource(request -> {
			CorsConfiguration configuration = new CorsConfiguration();
			configuration.applyPermitDefaultValues();
			return configuration;
		}));

		// disable CSRF
		http.csrf(AbstractHttpConfigurer::disable);

		// disable HTTP Basic
		http.httpBasic(AbstractHttpConfigurer::disable);

		// disable Form Login
		http.formLogin(AbstractHttpConfigurer::disable);

		// Session Management Policy - STATELESS
		http.sessionManagement(
			sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		http.authorizeHttpRequests(authorizeRequests ->
			authorizeRequests
				.requestMatchers("/", "/oauth2/**").permitAll()
				// .requestMatchers("/api/**").hasRole())
				// TODO: after adding `MemberRole`
				.anyRequest().authenticated());

		http.oauth2Login(
			oauth2Login -> oauth2Login.userInfoEndpoint(
				userinfoEndPoint -> userinfoEndPoint.oidcUserService(customOidcUserService)));

		// TODO: add filter
		return http.build();
	}

}
