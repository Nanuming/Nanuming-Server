package gdsc.nanuming.item.service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
import gdsc.nanuming.item.dto.response.AddItemResponse;
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
	private static final int MAIN_IMAGE_INDEX = 0;

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
		itemImageList.get(MAIN_IMAGE_INDEX).setAsMainImage();

		Item savedItem = itemRepository.save(newTemporaryItem);
		return AddItemResponse.from(savedItem.getId());
	}

	public ShowItemDetailResponse showItemDetail(Long itemId) {
		log.info(">>> ItemService showItemDetail()");

		Item item = itemRepository.findById(itemId)
			.orElseThrow(() -> new IllegalArgumentException("No item found."));

		List<String> itemImageUrlList = convertIntoItemImageUrlList(item.getItemImageList());
		String category = item.getCategory().getCategoryName().getName();
		String nickname = item.getSharer().getNickname();
		Location location = item.getLocker().getLocation();
		boolean isOwner = item.getSharer().getId().equals(getCurrentUserDetails().getId());
		String createdAt = item.getCreatedAt().format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));
		String updatedAt = item.getCreatedAt().format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));

		return ShowItemDetailResponse.of(itemId, itemImageUrlList, category,
			nickname, location.getName(),
			item.getDescription(), isOwner, createdAt, updatedAt);
	}

	private List<String> convertIntoItemImageUrlList(List<ItemImage> itemImageList) {
		return itemImageList.stream()
			.map(ItemImage::getItemImageUrl)
			.toList();
	}

	public List<ItemOutlineDto> convertIntoItemOutlineDtoList(List<Locker> occupiedLockerList) {
		List<ItemOutlineDto> itemOutlineDtoList = new ArrayList<>();
		for (Locker locker : occupiedLockerList) {
			Item item = locker.getItem();
			itemOutlineDtoList.add(ItemOutlineDto.of(item.getId(),
					item.getMainItemImage().getItemImageUrl(),
					item.getTitle(),
					locker.getLocation().getName(),
					String.valueOf(item.getCategory().getCategoryName().getName())
				)
			);
		}
		return itemOutlineDtoList;
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
