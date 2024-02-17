package gdsc.nanuming.member.controller;

import static gdsc.nanuming.common.code.CommonCode.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gdsc.nanuming.common.response.BaseResponseWithData;
import gdsc.nanuming.member.dto.request.AssignLockerRequest;
import gdsc.nanuming.member.dto.request.ConfirmImageRequest;
import gdsc.nanuming.member.dto.response.AssignLockerResponse;
import gdsc.nanuming.member.dto.response.ConfirmImageResponse;
import gdsc.nanuming.member.dto.response.ShowMemberItemList;
import gdsc.nanuming.member.dto.response.TemporaryItemDetailResponse;
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

	@GetMapping("/{memberId}/{itemId}")
	public BaseResponseWithData<TemporaryItemDetailResponse> showTemporaryItemDetail(
		@PathVariable Long memberId,
		@PathVariable Long itemId) {
		log.info(">>> Run MemberController showTemporaryItemDetail()");
		return BaseResponseWithData.of(RESPONSE_SUCCESS, memberService.showTemporaryItemDetail(memberId, itemId));
	}

	@PostMapping("/{memberId}/{itemId}/assign")
	public BaseResponseWithData<AssignLockerResponse> assignLocker(
		@PathVariable Long memberId,
		@PathVariable Long itemId,
		@RequestBody AssignLockerRequest assignLockerRequest) {
		log.info(">>> Run MemberController assignLocker()");
		return BaseResponseWithData.of(RESPONSE_SUCCESS,
			memberService.assignLocker(memberId, itemId, assignLockerRequest));
	}

	@PostMapping("/{memberId}/{itemId}/confirm")
	public BaseResponseWithData<ConfirmImageResponse> confirmImage(
		@PathVariable Long memberId,
		@PathVariable Long itemId,
		ConfirmImageRequest confirmImageRequest) {
		log.info(">>> Run MemberController confirmImage()");
		return BaseResponseWithData.of(RESPONSE_SUCCESS,
			memberService.confirmImage(memberId, itemId, confirmImageRequest));
	}
}
