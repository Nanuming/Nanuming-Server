package gdsc.nanuming.common.initializer;

import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import gdsc.nanuming.common.initializer.bulk.ItemBulkRepository;
import gdsc.nanuming.location.openapi.event.OpenApiLoadedEvent;
import gdsc.nanuming.location.repository.LocationRepository;
import gdsc.nanuming.locker.repository.LockerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ItemDataInitializer {

	private final ItemBulkRepository itemBulkRepository;
	private final LocationRepository locationRepository;
	private static final int BATCH_SIZE = 1000;

	@Order(4)
	@EventListener
	@Transactional
	public void itemDataInsert(OpenApiLoadedEvent event) {

		log.info("itemDataInsert start");
		long count = locationRepository.count();
		for (long i = 1; i <= count; i += BATCH_SIZE) {
			long end = Math.min(count, i + BATCH_SIZE);
			itemBulkRepository.bulkInsertItem(i, end);
		}
		log.info("itemDataInsert finished");
	}

}
