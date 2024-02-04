package gdsc.nanuming.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import gdsc.nanuming.security.service.CustomUserDetailsService;
import gdsc.nanuming.security.util.CustomUserDetails;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

	private final CustomUserDetailsService customUserDetailsService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String providerId = authentication.getName();

		try {
			CustomUserDetails userDetails = customUserDetailsService.loadUserByUsername(providerId);

			return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
		} catch (UsernameNotFoundException e) {
			throw new BadCredentialsException("No user found with providerId: " + providerId);
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}
}
