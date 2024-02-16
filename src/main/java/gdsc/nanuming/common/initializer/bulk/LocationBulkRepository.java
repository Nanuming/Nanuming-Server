package gdsc.nanuming.common.initializer.bulk;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import gdsc.nanuming.location.entity.Location;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
public class LocationBulkRepository {

	private final JdbcTemplate template;

	public void bulkInsertLocation(List<Location> locationList) {

		final String sql = "INSERT INTO location (latitude, longitude, address, name, point) VALUES (?, ?, ?, ?, ST_PointFromText(?, 4326))";

		List<Object[]> batchArgs = new ArrayList<>();

		for (Location location : locationList) {
			batchArgs.add(new Object[] {location.getLatitude(), location.getLongitude(), location.getAddress(),
				location.getName(), location.convertPointToText()});
		}

		template.batchUpdate(sql, batchArgs);

	}

}
