package gdsc.nanuming.location.service;

import java.util.ArrayList;
import java.util.List;

import org.locationtech.jts.geom.Polygon;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gdsc.nanuming.item.dto.ItemOutlineDto;
import gdsc.nanuming.item.entity.Item;
import gdsc.nanuming.item.entity.ItemStatus;
import gdsc.nanuming.item.service.ItemService;
import gdsc.nanuming.location.dto.LocationInfoDto;
import gdsc.nanuming.location.dto.request.NearLocationAndItemRequest;
import gdsc.nanuming.location.dto.response.NearLocationAndItemResponse;
import gdsc.nanuming.location.entity.Location;
import gdsc.nanuming.location.repository.LocationRepository;
import gdsc.nanuming.location.util.GeometryUtil;
import gdsc.nanuming.locker.entity.Locker;
import gdsc.nanuming.locker.service.LockerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LocationService {

	private final ItemService itemService;
	private final LockerService lockerService;

	private final LocationRepository locationRepository;

	public NearLocationAndItemResponse getNearLocationAndItemList(NearLocationAndItemRequest request) {
		Polygon polygon = GeometryUtil.createPolygon(request);
		List<Location> locationList = locationRepository.findLocationList(polygon);
		log.info("locationList.size(): {}", locationList.size());

		List<LocationInfoDto> locationInfoDtoList = locationList.stream()
			.map(location -> LocationInfoDto.of(location.getId(), location.getLatitude(), location.getLongitude()))
			.toList();

		List<ItemOutlineDto> itemOutlineDtoList = new ArrayList<>();

		for (LocationInfoDto locationInfoDto : locationInfoDtoList) {
			List<Locker> occupiedLockerList = lockerService.getOccupiedLockerList(locationInfoDto.getLocationId());
			for (Locker locker : occupiedLockerList) {
				if (locker.getItem().getItemStatus().equals(ItemStatus.AVAILABLE)) {
					Item item = locker.getItem();
					itemOutlineDtoList.add(ItemOutlineDto.of(
						item.getId(),
						item.getMainItemImage().getItemImageUrl(),
						item.getTitle(),
						locker.getLocation().getName(),
						item.getCategory().getCategoryName().getName()
					));
				}
			}
		}

		return NearLocationAndItemResponse.of(locationInfoDtoList, itemOutlineDtoList);
	}

}
