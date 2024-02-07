package gdsc.nanuming.item.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gdsc.nanuming.image.entity.ItemImage;
import gdsc.nanuming.image.repository.ItemImageRepository;
import gdsc.nanuming.item.dto.request.AddItemRequest;
import gdsc.nanuming.item.dto.response.AddItemResponse;
import gdsc.nanuming.item.entity.Item;
import gdsc.nanuming.item.repository.ItemRepository;
import gdsc.nanuming.locker.entity.Locker;
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
	private final ItemImageRepository itemImageRepository;

	@Transactional
	public AddItemResponse addItem(AddItemRequest addItemRequest) {
		log.info(">>> Run ItemService addItem()");

		Member sharer = memberRepository.findById(addItemRequest.getSharerId())
			.orElseThrow(() -> new IllegalArgumentException("No member found."));
		log.info(">>> ItemService addItem() sharer: {}", sharer);

		Locker locker = lockerRepository.findById(addItemRequest.getLockerId())
			.orElseThrow(() -> new IllegalArgumentException("No Locker found."));
		log.info(">>> ItemService addItem() locker: {}", locker);

		List<ItemImage> itemImageList = new ArrayList<>();
		for (String itemImageUrl : addItemRequest.getImageList()) {
			ItemImage savedItemImage = itemImageRepository.save(ItemImage.from(itemImageUrl));
			itemImageList.add(savedItemImage);
		}

		Item newItem = addItemRequest.toEntity(sharer, locker);
		log.info(">>> temporary newItem: {}", newItem);

		newItem.addItemImageList(itemImageList);
		// TODO: remove magic number
		newItem.addMainItemImage(itemImageList.get(0));
		log.info(">>> newItem creation complete: {}", newItem);

		Item savedItem = itemRepository.save(newItem);
		return AddItemResponse.from(savedItem.getId());
	}

}
