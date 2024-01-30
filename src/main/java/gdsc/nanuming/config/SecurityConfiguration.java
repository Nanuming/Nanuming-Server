package gdsc.nanuming.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableWebSecurity(debug = true)
@RequiredArgsConstructor
public class SecurityConfiguration {

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
	}

}
