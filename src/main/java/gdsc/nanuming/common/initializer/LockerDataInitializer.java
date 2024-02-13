package gdsc.nanuming.common.initializer;

import static gdsc.nanuming.locker.entity.LockerSize.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import gdsc.nanuming.location.entity.Location;
import gdsc.nanuming.location.openapi.event.OpenApiLoadedEvent;
import gdsc.nanuming.location.repository.LocationRepository;
import gdsc.nanuming.locker.entity.Locker;
import gdsc.nanuming.locker.repository.LockerRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LockerDataInitializer {

	private final LockerRepository lockerRepository;
	private final LocationRepository locationRepository;
	private static final int SMALL_COUNT = 3;
	private static final int MIDDLE_COUNT = 2;
	private static final int BIG_COUNT = 1;

	@EventListener
	private void lockerDataInsert(OpenApiLoadedEvent event) {
		long count = locationRepository.count();
		for (long i = 1; i <= count; i++) {
			List<Locker> lockerList = new ArrayList<>();
			Location location = locationRepository.findById(i).get();
			for (int j = 0; j < SMALL_COUNT; j++) {
				lockerList.add(Locker.of(location, SMALL));
			}
			for (int j = 0; j < MIDDLE_COUNT; j++) {
				lockerList.add(Locker.of(location, MIDDLE));
			}
			for (int j = 0; j < BIG_COUNT; j++) {
				lockerList.add(Locker.of(location, BIG));
			}
			lockerRepository.saveAll(lockerList);
		}
	}

}
