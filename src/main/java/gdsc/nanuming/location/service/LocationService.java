package gdsc.nanuming.location.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gdsc.nanuming.location.entity.Location;
import gdsc.nanuming.location.repository.LocationRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LocationService {

	private final LocationRepository locationRepository;

	@Transactional
	public void saveLocationList(List<Location> locationList) {
		locationRepository.saveAll(locationList);
	}
}
