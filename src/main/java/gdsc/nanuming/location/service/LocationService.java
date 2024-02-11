package gdsc.nanuming.location.service;

import java.util.List;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gdsc.nanuming.location.dto.request.ShowNearLocationListRequest;
import gdsc.nanuming.location.dto.request.ShowNearLocationListRequestWithDistanceMeter;
import gdsc.nanuming.location.dto.response.ShowNearLocationListResponse;
import gdsc.nanuming.location.entity.Location;
import gdsc.nanuming.location.repository.LocationRepository;
import gdsc.nanuming.location.util.GeometryUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LocationService {

	private final LocationRepository locationRepository;

	@Transactional
	public void saveLocationList(List<Location> locationList) {
		locationRepository.saveAll(locationList);
	}

	public ShowNearLocationListResponse showNearLocationListAndItemCount(ShowNearLocationListRequest request) {
		Polygon polygon = GeometryUtil.createPolygon(request);
		log.info("polygon: {}", polygon);
		List<Location> locations = locationRepository.findLocations(polygon);
		for (Location location : locations) {
			log.info("location.getId: {}", location.getId());
			log.info("location.getDescription(): {}", location.getDescription());
		}
		return null;
	}

	public ShowNearLocationListResponse showNearLocationListWithDistanceMeterAndItemCount(
		ShowNearLocationListRequestWithDistanceMeter request) {

		double halfSideLength = request.getRadiusInKm() / (111.32 * Math.cos(Math.toRadians(request.getLatitude())));
		Coordinate[] coordinates = new Coordinate[5];
		coordinates[0] = new Coordinate(request.getLongitude() - halfSideLength,
			request.getLatitude() - halfSideLength);
		coordinates[1] = new Coordinate(request.getLongitude() + halfSideLength,
			request.getLatitude() - halfSideLength);
		coordinates[2] = new Coordinate(request.getLongitude() + halfSideLength,
			request.getLatitude() + halfSideLength);
		coordinates[3] = new Coordinate(request.getLongitude() - halfSideLength,
			request.getLatitude() + halfSideLength);
		coordinates[4] = coordinates[0];

		Polygon square = GeometryUtil.createPolygon(coordinates);

		Point point = GeometryUtil.createPoint(request.getLongitude(), request.getLatitude());
		Long distanceMeter = (long)(request.getRadiusInKm() * 1000);
		List<Location> locations = locationRepository.findLocationsByDistanceMeter(square, point, distanceMeter);

		for (Location location : locations) {
			log.info("location.getId: {}", location.getId());
			log.info("location.getDescription: {}", location.getDescription());
		}
		return null;
	}
}
