package gdsc.nanuming.common.initializer;

import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import gdsc.nanuming.common.initializer.bulk.LockerBulkRepository;
import gdsc.nanuming.location.openapi.event.OpenApiLoadedEvent;
import gdsc.nanuming.location.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class LockerDataInitializer {

	private final LockerBulkRepository lockerBulkRepository;
	private final LocationRepository locationRepository;

	private static final int BATCH_SIZE = 1000;

	@Order(2)
	@EventListener
	@Transactional
	public void lockerDataInsert(OpenApiLoadedEvent event) {

		log.info("lockerDataInsert start");
		long locationCount = locationRepository.count();
		for (long i = 1; i <= locationCount; i += BATCH_SIZE) {
			long end = Math.min(locationCount, i + BATCH_SIZE);
			lockerBulkRepository.bulkInsertLocker(i, end);
		}
		log.info("lockerDataInsert finished");
	}

}
