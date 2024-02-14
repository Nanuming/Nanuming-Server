package gdsc.nanuming.item.service;

import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gdsc.nanuming.category.entity.Category;
import gdsc.nanuming.category.repository.CategoryRepository;
import gdsc.nanuming.image.entity.ItemImage;
import gdsc.nanuming.image.service.ImageService;
import gdsc.nanuming.item.dto.ItemOutlineDto;
import gdsc.nanuming.item.dto.request.AddItemRequest;
import gdsc.nanuming.item.dto.request.AssignLockerRequest;
import gdsc.nanuming.item.dto.response.AddItemResponse;
import gdsc.nanuming.item.dto.response.AssignLockerResponse;
import gdsc.nanuming.item.dto.response.ShowItemDetailResponse;
import gdsc.nanuming.item.entity.Item;
import gdsc.nanuming.item.repository.ItemRepository;
import gdsc.nanuming.location.entity.Location;
import gdsc.nanuming.locker.entity.Locker;
import gdsc.nanuming.locker.repository.LockerRepository;
import gdsc.nanuming.member.entity.Member;
import gdsc.nanuming.member.repository.MemberRepository;
import gdsc.nanuming.security.util.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {

	private final ItemRepository itemRepository;
	private final MemberRepository memberRepository;
	private final LockerRepository lockerRepository;
	private final CategoryRepository categoryRepository;

	private final ImageService imageService;

	private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	private static final int THUMBNAIL_INDEX = 0;

	@Transactional
	public AddItemResponse addTemporaryItem(AddItemRequest addItemRequest) {
		log.info(">>> Run ItemService addTemporaryItem()");

		Member sharer = memberRepository.findById(addItemRequest.getSharerId())
			.orElseThrow(() -> new IllegalArgumentException("No member found."));
		log.info(">>> ItemService addItem() sharer: {}", sharer);

		Category category = categoryRepository.findById(addItemRequest.getCategoryId())
			.orElseThrow(() -> new IllegalArgumentException("No category found."));
		log.info(">>> category: {}", category.toString());

		Item newTemporaryItem = addItemRequest.toEntity(sharer, category);
		log.info(">>> temporary newTemporaryItem: {}", newTemporaryItem);

		Item temporarySavedItem = itemRepository.save(newTemporaryItem);
		List<ItemImage> itemImageList = imageService.uploadItemImage(addItemRequest.getImageList(), temporarySavedItem);
		temporarySavedItem.addItemImageList(itemImageList);
		temporarySavedItem.addMainItemImage(itemImageList.get(THUMBNAIL_INDEX));

		Item savedItem = itemRepository.save(newTemporaryItem);
		return AddItemResponse.from(savedItem.getId());
	}

	public ShowItemDetailResponse showItemDetail(Long itemId) {
		log.info(">>> ItemService showItemDetail()");

		Item item = itemRepository.findById(itemId)
			.orElseThrow(() -> new IllegalArgumentException("No item found."));

		List<String> itemImageUrlList = convertIntoItemImageUrlList(item.getItemImageList());
		String category = item.getCategory().getCategoryName().getName();
		String nickname = getCurrentUserDetails().getNickname();
		Location location = item.getLocker().getLocation();
		boolean isOwner = item.getSharer().getId().equals(getCurrentUserDetails().getId());
		String createdAt = item.getCreatedAt().format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));
		String updatedAt = item.getCreatedAt().format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));

		return ShowItemDetailResponse.of(itemId, itemImageUrlList, category,
			nickname, location.getName(),
			item.getDescription(), isOwner, createdAt, updatedAt);
	}

	@Transactional
	public AssignLockerResponse assignLocker(Long itemId, AssignLockerRequest assignLockerRequest) {
		log.info(">>> ItemService assignLocker()");

		Item item = itemRepository.findById(itemId)
			.orElseThrow(() -> new IllegalArgumentException("No item found."));

		CustomUserDetails currentUserDetails = getCurrentUserDetails();
		if (!item.getSharer().getId().equals(currentUserDetails.getId())) {
			throw new IllegalStateException("Not permitted member.");
		}

		Locker locker = lockerRepository.findById(assignLockerRequest.getLockerId())
			.orElseThrow(() -> new IllegalArgumentException("No locker found."));

		item.assignLocker(locker);

		return AssignLockerResponse.of(item.getId(), locker.getId());
	}

	private List<String> convertIntoItemImageUrlList(List<ItemImage> itemImageList) {
		return itemImageList.stream()
			.map(ItemImage::getItemImageUrl)
			.toList();
	}

	private ItemOutlineDto convertIntoItemOutlineDto(Item item) {
		Long itemId = item.getId();
		String mainImageUrl = item.getMainItemImage().getItemImageUrl();
		String title = item.getTitle();
		String locationName = item.getLocker().getLocation().getName();
		String categoryName = item.getCategory().getCategoryName().getName();
		// TODO: need refactoring here or place `locationDescription` field in `Item`
		return ItemOutlineDto.of(itemId, mainImageUrl, title, locationName, categoryName);
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
