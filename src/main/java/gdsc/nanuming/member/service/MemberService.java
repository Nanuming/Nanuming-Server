package gdsc.nanuming.member.service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gdsc.nanuming.image.entity.ItemImage;
import gdsc.nanuming.image.service.ImageService;
import gdsc.nanuming.item.dto.ItemOutlineDto;
import gdsc.nanuming.item.entity.Item;
import gdsc.nanuming.item.entity.ItemStatus;
import gdsc.nanuming.item.repository.ItemRepository;
import gdsc.nanuming.locker.entity.Locker;
import gdsc.nanuming.locker.repository.LockerRepository;
import gdsc.nanuming.member.dto.request.AssignLockerRequest;
import gdsc.nanuming.member.dto.request.ConfirmImageRequest;
import gdsc.nanuming.member.dto.response.AssignLockerResponse;
import gdsc.nanuming.member.dto.response.ConfirmImageResponse;
import gdsc.nanuming.member.dto.response.ShowMemberItemList;
import gdsc.nanuming.member.dto.response.TemporaryItemDetailResponse;
import gdsc.nanuming.security.util.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

	private final LockerRepository lockerRepository;
	private final ItemRepository itemRepository;

	private final ImageService imageService;

	private static final String NOT_ASSIGNED = "미보관";
	private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

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

	@Transactional
	public AssignLockerResponse assignLocker(Long memberId, Long itemId, AssignLockerRequest request) {
		log.info(">>> MemberService assignLocker()");

		if (!Objects.equals(getCurrentUserDetails().getId(), memberId)) {
			throw new IllegalArgumentException("Not permitted.");
		}

		Item item = itemRepository.findById(itemId)
			.orElseThrow(() -> new IllegalArgumentException("No item found"));

		Long sharerId = item.getSharer().getId();
		if (!Objects.equals(sharerId, memberId) || !Objects.equals(sharerId, getCurrentUserDetails().getId())) {
			throw new IllegalArgumentException("Not permitted.");
		}

		Locker locker = lockerRepository.findById(request.getLockerId())
			.orElseThrow(() -> new IllegalArgumentException("No locker found."));

		item.assignLocker(locker);

		return AssignLockerResponse.of(item.getId(), locker.getId());
	}

	@Transactional
	public ConfirmImageResponse confirmImage(Long memberId, Long itemId, ConfirmImageRequest request) {
		log.info(">>> MemberService confirmImage()");

		if (!Objects.equals(getCurrentUserDetails().getId(), memberId)) {
			throw new IllegalArgumentException("Not permitted.");
		}

		Item temporarySavedItem = itemRepository.findById(itemId)
			.orElseThrow(() -> new IllegalArgumentException("No item found"));

		Long sharerId = temporarySavedItem.getSharer().getId();
		if (!Objects.equals(sharerId, memberId) || !Objects.equals(sharerId, getCurrentUserDetails().getId())) {
			throw new IllegalArgumentException("Not permitted.");
		}

		log.info(">>> confirmImage: {}", request.getConfirmImage());
		ItemImage confirmItemImage = imageService
			.uploadConfirmItemImage(request.getConfirmImage(), temporarySavedItem);

		confirmItemImage.setAsConfirmImage();
		temporarySavedItem.getItemImageList().add(confirmItemImage);
		temporarySavedItem.changeSaveStatusToAvailable();

		return ConfirmImageResponse.from(confirmItemImage.getItemImageId());
	}

	public TemporaryItemDetailResponse showTemporaryItemDetail(Long memberId, Long itemId) {
		log.info(">>> MemberService showTemporaryItemDetail()");

		if (!Objects.equals(getCurrentUserDetails().getId(), memberId)) {
			throw new IllegalArgumentException("Not permitted.");
		}

		Item temporarySavedItem = itemRepository.findById(itemId)
			.orElseThrow(() -> new IllegalArgumentException("No item found"));

		Long sharerId = temporarySavedItem.getSharer().getId();
		if (!Objects.equals(sharerId, memberId) || !Objects.equals(sharerId, getCurrentUserDetails().getId())) {
			throw new IllegalArgumentException("Not permitted.");
		}

		String createdAt = temporarySavedItem.getCreatedAt().format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));
		String updatedAt = temporarySavedItem.getCreatedAt().format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));

		return TemporaryItemDetailResponse.of(
			temporarySavedItem.getId(),
			convertIntoItemImageUrlList(temporarySavedItem.getItemImageList()),
			temporarySavedItem.getCategory().getCategoryName().getName(),
			NOT_ASSIGNED,
			temporarySavedItem.getSharer().getNickname(),
			temporarySavedItem.getDescription(),
			true,
			createdAt,
			updatedAt
		);
	}

	private List<String> convertIntoItemImageUrlList(List<ItemImage> itemImageList) {
		return itemImageList.stream()
			.map(ItemImage::getItemImageUrl)
			.toList();
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
