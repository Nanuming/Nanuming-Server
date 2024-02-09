package gdsc.nanuming.security.util;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Builder;
import lombok.Getter;

public class CustomUserDetails implements UserDetails {

	private String providerId;

	@Getter
	private String email;

	@Getter
	private String nickname;

	private Collection<? extends GrantedAuthority> authorities;

	@Builder
	private CustomUserDetails(String providerId, String email, String nickname,
		Collection<? extends GrantedAuthority> authorities) {
		this.providerId = providerId;
		this.email = email;
		this.nickname = nickname;
		this.authorities = authorities;
	}

	public static CustomUserDetails of(String providerId, String email, String nickname,
		Collection<? extends GrantedAuthority> authorities) {
		return CustomUserDetails.builder()
			.providerId(providerId)
			.email(email)
			.nickname(nickname)
			.authorities(authorities)
			.build();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return null;
	}

	@Override
	public String getUsername() {
		return providerId;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
