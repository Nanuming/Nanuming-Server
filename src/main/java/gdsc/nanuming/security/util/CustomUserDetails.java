package gdsc.nanuming.security.util;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CustomUserDetails implements UserDetails {

	private Long id;

	private String providerId;

	private String nickname;

	private Collection<? extends GrantedAuthority> authorities;

	@Builder
	private CustomUserDetails(Long id, String providerId, String nickname,
		Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.providerId = providerId;
		this.nickname = nickname;
		this.authorities = authorities;
	}

	public static CustomUserDetails of(Long id, String providerId, String nickname,
		Collection<? extends GrantedAuthority> authorities) {
		return CustomUserDetails.builder()
			.id(id)
			.providerId(providerId)
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
