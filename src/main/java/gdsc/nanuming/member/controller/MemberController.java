package gdsc.nanuming.member.controller;

import static gdsc.nanuming.common.code.CommonCode.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gdsc.nanuming.common.response.BaseResponseWithData;
import gdsc.nanuming.member.dto.response.ShowMemberItemList;
import gdsc.nanuming.member.repository.MemberRepository;
import gdsc.nanuming.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/profile")
public class MemberController {

	private final MemberRepository memberRepository;
	private final MemberService memberService;

	@GetMapping("/{memberId}")
	public BaseResponseWithData<ShowMemberItemList> showItemListByItemStatus(
		@PathVariable long memberId,
		@RequestParam String itemStatus) {
		log.info(">>> Run MemberController showItemListByItemStatus()");
		return BaseResponseWithData.of(RESPONSE_SUCCESS, memberService.showItemListByItemStatus(memberId, itemStatus));
	}

}
