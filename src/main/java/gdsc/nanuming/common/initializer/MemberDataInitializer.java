package gdsc.nanuming.common.initializer;

import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import gdsc.nanuming.location.openapi.event.OpenApiLoadedEvent;
import gdsc.nanuming.member.entity.Member;
import gdsc.nanuming.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class MemberDataInitializer {

	private final MemberRepository memberRepository;

	@Order(3)
	@EventListener
	@Transactional
	public void memberDataInsert(OpenApiLoadedEvent event) {

		log.info("memberDataInsert start");
		memberRepository.save(Member.of("고뭉남", "google_12345"));
		memberRepository.save(Member.of("고봉렬", "google_23456"));
		memberRepository.save(Member.of("고건호", "google_34567"));
		log.info("memberDataInsert finished");

	}

}
