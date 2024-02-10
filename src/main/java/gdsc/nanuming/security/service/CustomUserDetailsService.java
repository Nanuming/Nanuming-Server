package gdsc.nanuming.security.service;

import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gdsc.nanuming.member.entity.Member;
import gdsc.nanuming.member.repository.MemberRepository;
import gdsc.nanuming.security.util.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private final MemberRepository memberRepository;

	@Override
	@Transactional
	public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info(">>> CustomUserDetailsService loadUserByUsername username: {}", username);
		// TODO: need custom exception processing here
		return memberRepository.findByProviderId(username)
			.map(this::createCustomUserDetails)
			.orElseThrow(() -> new UsernameNotFoundException(username + " -> DB에 존재하지 않는 providerId"));
	}

	private CustomUserDetails createCustomUserDetails(Member member) {

		log.info(">>> CustomUserDetailsService createCustomUserDetails()");

		GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(member.getRole().getValue());

		log.info(">>> CustomUserDetailsService createCustomUserDetails() grantedAuthority: {}", grantedAuthority);

		return CustomUserDetails.of(
			member.getId(),
			member.getProviderId(),
			member.getNickname(),
			Collections.singleton(grantedAuthority));
	}
}
