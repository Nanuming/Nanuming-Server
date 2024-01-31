package gdsc.nanuming.member.service;

import org.springframework.stereotype.Service;

import gdsc.nanuming.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;

}
