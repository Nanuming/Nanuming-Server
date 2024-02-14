package gdsc.nanuming.location.util;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.geom.PrecisionModel;

import gdsc.nanuming.common.UserMapPolygon;
import gdsc.nanuming.location.dto.request.ShowNearLocationListRequest;

public class GeometryUtil {

	private static final GeometryFactory GEOMETRY_FACTORY = new GeometryFactory(new PrecisionModel(), 4326);

	public static Point createPoint(double longitude, double latitude) {
		return GEOMETRY_FACTORY.createPoint(new Coordinate(longitude, latitude));
	}

	public static Polygon createPolygon(UserMapPolygon userMapPolygon) {

		double latitude = userMapPolygon.getLatitude();
		double longitude = userMapPolygon.getLongitude();
		double latitudeDelta = userMapPolygon.getLatitudeDelta();
		double longitudeDelta = userMapPolygon.getLongitudeDelta();

		double minLatitude = latitude - latitudeDelta;
		double maxLatitude = latitude + latitudeDelta;
		double minLongitude = longitude - longitudeDelta;
		double maxLongitude = longitude + longitudeDelta;

		Coordinate[] shell = new Coordinate[] {
			new Coordinate(maxLongitude, minLatitude),
			new Coordinate(maxLongitude, maxLatitude),
			new Coordinate(minLongitude, maxLatitude),
			new Coordinate(minLongitude, minLatitude),
			new Coordinate(maxLongitude, minLatitude)
		};

		return GEOMETRY_FACTORY.createPolygon(shell);
	}

	public static Polygon createPolygon(Coordinate[] square) {

		return GEOMETRY_FACTORY.createPolygon(square);
	}

}
