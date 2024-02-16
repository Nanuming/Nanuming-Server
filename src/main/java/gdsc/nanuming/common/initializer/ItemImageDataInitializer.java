package gdsc.nanuming.common.initializer;

import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import gdsc.nanuming.common.initializer.bulk.ItemImageBulkRepository;
import gdsc.nanuming.item.repository.ItemRepository;
import gdsc.nanuming.location.openapi.event.OpenApiLoadedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ItemImageDataInitializer {

	private final ItemRepository itemRepository;
	private final ItemImageBulkRepository itemImageBulkRepository;

	private static final int BATCH_SIZE = 1000;

	@Order(5)
	@EventListener
	@Transactional
	public void itemImageDataInsert(OpenApiLoadedEvent event) {

		log.info("itemImageDataInsert start");
		long itemCount = itemRepository.count();
		for (long i = 1; i <= itemCount; i += BATCH_SIZE) {
			long end = Math.min(itemCount, i + BATCH_SIZE);
			itemImageBulkRepository.bulkInsertItemImage(i, end);
		}
		log.info("itemImageDataInsert end");
	}

}
