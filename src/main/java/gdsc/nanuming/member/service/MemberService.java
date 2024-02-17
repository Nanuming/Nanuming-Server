package gdsc.nanuming.member.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gdsc.nanuming.item.dto.ItemOutlineDto;
import gdsc.nanuming.item.entity.Item;
import gdsc.nanuming.item.entity.ItemStatus;
import gdsc.nanuming.item.repository.ItemRepository;
import gdsc.nanuming.member.dto.response.ShowMemberItemList;
import gdsc.nanuming.member.repository.MemberRepository;
import gdsc.nanuming.security.util.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

	private final MemberRepository memberRepository;
	private final ItemRepository itemRepository;

	private static final String NOT_ASSIGNED = "미보관";

	public ShowMemberItemList showItemListByItemStatus(long memberId, String status) {

		log.info("MemberService showItemListByItemStatus()");
		ItemStatus itemStatus = ItemStatus.valueOf(status.toUpperCase());
		if (memberId != getCurrentUserDetails().getId()) {
			throw new IllegalArgumentException("Not permitted.");
		}

		List<Item> itemListByItemStatus = itemRepository.findAllBySharerIdAndItemStatus(memberId, itemStatus);

		List<ItemOutlineDto> itemOutlineDtoList = new ArrayList<>();

		for (Item item : itemListByItemStatus) {

			String locationName = Optional.ofNullable(item.getLocker())
				.map(locker -> locker.getLocation().getName())
				.orElse(NOT_ASSIGNED);

			itemOutlineDtoList.add(ItemOutlineDto.of(
					item.getId(),
					item.getMainItemImage().getItemImageUrl(),
					item.getTitle(),
					locationName,
					item.getCategory().getCategoryName().getName()
				)
			);
		}

		return ShowMemberItemList.from(itemOutlineDtoList);
	}

	private CustomUserDetails getCurrentUserDetails() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
			return (CustomUserDetails)authentication.getPrincipal();
		} else {
			throw new IllegalStateException("User is not authenticated.");
		}
	}
}
