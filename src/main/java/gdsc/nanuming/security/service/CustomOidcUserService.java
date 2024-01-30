package gdsc.nanuming.security.service;

import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.stereotype.Service;

import gdsc.nanuming.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOidcUserService extends OidcUserService {

	private static final String EMAIL = "email";
	private static final String REGISTERED = "registered";

	private final MemberRepository memberRepository;

}
