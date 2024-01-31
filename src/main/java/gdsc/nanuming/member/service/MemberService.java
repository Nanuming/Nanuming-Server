package gdsc.nanuming.member.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gdsc.nanuming.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

	private final MemberRepository memberRepository;

}
