package gdsc.nanuming.item.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gdsc.nanuming.category.entity.Category;
import gdsc.nanuming.category.repository.CategoryRepository;
import gdsc.nanuming.image.entity.ItemImage;
import gdsc.nanuming.image.repository.ItemImageRepository;
import gdsc.nanuming.item.dto.ItemOutlineDto;
import gdsc.nanuming.item.dto.request.AddItemRequest;
import gdsc.nanuming.item.dto.response.AddItemResponse;
import gdsc.nanuming.item.dto.response.ShowItemListResponse;
import gdsc.nanuming.item.entity.Item;
import gdsc.nanuming.item.repository.ItemRepository;
import gdsc.nanuming.location.entity.Location;
import gdsc.nanuming.location.repository.LocationRepository;
import gdsc.nanuming.locker.entity.Locker;
import gdsc.nanuming.locker.entity.LockerStatus;
import gdsc.nanuming.locker.repository.LockerRepository;
import gdsc.nanuming.member.entity.Member;
import gdsc.nanuming.member.repository.MemberRepository;
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
	private final LocationRepository locationRepository;
	private final ItemImageRepository itemImageRepository;

	@Transactional
	public AddItemResponse addTemporaryItem(AddItemRequest addItemRequest) {
		log.info(">>> Run ItemService addTemporaryItem()");

		Member sharer = memberRepository.findById(addItemRequest.getSharerId())
			.orElseThrow(() -> new IllegalArgumentException("No member found."));
		log.info(">>> ItemService addItem() sharer: {}", sharer);

		List<ItemImage> itemImageList = new ArrayList<>();
		for (String itemImageUrl : addItemRequest.getImageList()) {
			ItemImage savedItemImage = itemImageRepository.save(ItemImage.from(itemImageUrl));
			itemImageList.add(savedItemImage);
		}

		Category category = categoryRepository.findById(addItemRequest.getCategoryId())
			.orElseThrow(() -> new IllegalArgumentException("No category found."));
		log.info(">>> category: {}", category.getCategoryName());

		Item newTemporaryItem = addItemRequest.toEntity(sharer, category);
		log.info(">>> temporary newTemporaryItem: {}", newTemporaryItem);

		newTemporaryItem.addItemImageList(itemImageList);
		// TODO: remove magic number
		newTemporaryItem.addMainItemImage(itemImageList.get(0));
		log.info(">>> newTemporaryItem creation complete: {}", newTemporaryItem);

		Item savedItem = itemRepository.save(newTemporaryItem);
		return AddItemResponse.from(savedItem.getId());
	}

	public ShowItemListResponse showItemList(long locationId) {
		Location location = locationRepository.findById(locationId)
			.orElseThrow(() -> new IllegalArgumentException("No Location found."));

		List<Locker> containedLockerList = location.getLockerList().stream()
			.filter(locker -> locker.getStatus() == LockerStatus.CONTAINED).toList();

		List<Item> itemList = containedLockerList.stream()
			.flatMap(locker -> itemRepository.findByLockerId(locker.getId()).stream())
			.toList();

		List<ItemOutlineDto> itemOutlineDtoList = itemList.stream()
			.map(this::convertIntoItemOutlineDto)
			.toList();

		return ShowItemListResponse.from(itemOutlineDtoList);
	}

	private ItemOutlineDto convertIntoItemOutlineDto(Item item) {

		Long itemId = item.getId();
		String mainImageUrl = item.getMainItemImage().getItemImageUrl();
		String title = item.getTitle();
		String locationDescription = item.getLocker().getLocation().getDescription();
		String categoryName = item.getCategory().getCategoryName().getName();
		// TODO: need refactoring here or place `locationDescription` field in `Item`
		return ItemOutlineDto.of(itemId, mainImageUrl, title, locationDescription, categoryName);
	}
}
