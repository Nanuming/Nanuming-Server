package gdsc.nanuming.common.config;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import gdsc.nanuming.category.entity.Category;
import gdsc.nanuming.category.entity.CategoryName;
import gdsc.nanuming.category.repository.CategoryRepository;
import gdsc.nanuming.item.repository.ItemRepository;
import gdsc.nanuming.location.entity.Location;
import gdsc.nanuming.location.repository.LocationRepository;
import gdsc.nanuming.locker.entity.Locker;
import gdsc.nanuming.locker.entity.LockerSize;
import gdsc.nanuming.locker.repository.LockerRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Profile({"local", "prod"})
public class DataLoader implements ApplicationRunner {

	private final LockerRepository lockerRepository;
	private final CategoryRepository categoryRepository;
	private final LocationRepository locationRepository;

	@Override
	public void run(ApplicationArguments args) throws Exception {

		// CategoryRepository initialize
		if (categoryRepository.count() == 0) {
			categoryRepository.save(Category.from(CategoryName.TOY));
			categoryRepository.save(Category.from(CategoryName.BOOK));
			categoryRepository.save(Category.from(CategoryName.CLOTHES));
			categoryRepository.save(Category.from(CategoryName.NURTURE_PRODUCT));
			categoryRepository.save(Category.from(CategoryName.ETC));
		}

		// Location Repository
		// TODO: this part must be removed after open api fetching site renewal on 02.12
		if (locationRepository.count() == 0) {
			locationRepository.save(Location.of("진관동 은평한옥마을성균관어린이집", 37.6395989, 126.9402267));
			locationRepository.save(Location.of("응암동 은행나무어린이집", 37.5911996, 126.9206564));
			locationRepository.save(Location.of("녹번동 구립녹번복지관어린이집", 37.6027499, 126.9318596));

			locationRepository.save(Location.of("자양동 구립효지어린이집", 37.5360961, 127.0738473));
			locationRepository.save(Location.of("자양동 자양어린이집", 37.5328982, 127.0752311));
			locationRepository.save(Location.of("자양동 민들레어린이집", 37.5288547, 127.0800218));

			locationRepository.save(Location.of("화곡동 미소담어린이집", 37.5287936, 126.8422259));
			locationRepository.save(Location.of("내발산동 발산어린이집", 37.5544534, 126.8421917));
			locationRepository.save(Location.of("마곡동 마곡9나인어린이집", 37.5556057, 126.8456432));
		}

		// LockerRepository initialize
		if (lockerRepository.count() == 0) {
			for (int i = 1; i <= locationRepository.count(); i++) {
				Location location = locationRepository.findById((long)i).get();
				for (int j = 0; j < 6; j++) {
					lockerRepository.save(Locker.of(location, LockerSize.SMALL));
				}
				for (int j = 0; j < 4; j++) {
					lockerRepository.save(Locker.of(location, LockerSize.MIDDLE));
				}
				for (int j = 0; j < 2; j++) {
					lockerRepository.save(Locker.of(location, LockerSize.BIG));
				}
			}
		}
	}
}
