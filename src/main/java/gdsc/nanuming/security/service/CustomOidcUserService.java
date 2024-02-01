// package gdsc.nanuming.security.service;
//
// import java.util.Collections;
// import java.util.Map;
// import java.util.Optional;
//
// import org.springframework.security.core.authority.SimpleGrantedAuthority;
// import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
// import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
// import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
// import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
// import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
// import org.springframework.security.oauth2.core.oidc.user.OidcUser;
// import org.springframework.stereotype.Service;
//
// import gdsc.nanuming.member.MemberRole;
// import gdsc.nanuming.member.entity.Member;
// import gdsc.nanuming.member.service.MemberService;
// import gdsc.nanuming.security.attributes.OidcAttributes;
// import lombok.RequiredArgsConstructor;
// import lombok.extern.slf4j.Slf4j;
//
// @Slf4j
// @Service
// @RequiredArgsConstructor
// public class CustomOidcUserService extends OidcUserService {
//
// 	private static final String EMAIL = "email";
// 	private static final String REGISTERED = "registered";
//
// 	private final MemberService memberService;
//
// 	@Override
// 	public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
//
// 		OidcUser oidcUser = super.loadUser(userRequest);
//
// 		String registrationId = userRequest.getClientRegistration().getRegistrationId();
// 		log.info(">>> CustomOidcUserService loadUser() registrationId: {}", registrationId);
// 		log.info(">>> registrationId now used as `provider`");
//
// 		String userNameAttributeName = userRequest.getClientRegistration()
// 			.getProviderDetails()
// 			.getUserInfoEndpoint()
// 			.getUserNameAttributeName();
// 		log.info(">>> CustomOidcUserService loadUser() userNameAttributeName: {}", userNameAttributeName);
//
// 		OidcAttributes oidcAttribute = OidcAttributes.of(registrationId, userNameAttributeName,
// 			oidcUser.getAttributes());
//
// 		Map<String, Object> memberAttribute = oidcAttribute.convertToMap();
// 		log.info(">>> CustomOidcUserService loadUser() memberAttribute: {}", memberAttribute);
//
// 		String email = (String)memberAttribute.get(EMAIL);
//
// 		Optional<Member> findMember = memberService.findByEmail(email);
//
// 		if (findMember.isEmpty()) {
// 			memberAttribute.put(REGISTERED, false);
// 			OidcUserInfo userInfo = new OidcUserInfo(memberAttribute);
// 			return new DefaultOidcUser(
// 				Collections.singleton(new SimpleGrantedAuthority(MemberRole.GUEST.getValue())),
// 				oidcUser.getIdToken(), userInfo
// 			);
// 		}
//
// 		memberAttribute.put(REGISTERED, true);
// 		OidcUserInfo userInfo = new OidcUserInfo(memberAttribute);
// 		return new DefaultOidcUser(
// 			Collections.singleton(new SimpleGrantedAuthority(MemberRole.USER.getValue())),
// 			oidcUser.getIdToken(), userInfo
// 		);
// 	}
// }
