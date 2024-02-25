package gdsc.nanuming.location.repository;

import java.util.List;

import org.locationtech.jts.geom.Polygon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import gdsc.nanuming.location.entity.Location;

public interface LocationRepository extends JpaRepository<Location, Long> {

	@Query(value = """
		SELECT
		location.*
		FROM location
		WHERE ST_CONTAINS(:area, location.point)""",
		nativeQuery = true)
	List<Location> findLocationList(@Param("area") Polygon area);
}
