package gdsc.nanuming.location.entity;

import java.util.List;

import org.locationtech.jts.geom.Point;

import gdsc.nanuming.location.util.GeometryUtil;
import gdsc.nanuming.locker.entity.Locker;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Location {

	@Id
	@Column(name = "location_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String description;

	private double latitude;

	private double longitude;

	@Column(columnDefinition = "Point")
	private Point point;

	@OneToMany(mappedBy = "location")
	private List<Locker> lockerList;

	@Builder
	private Location(String description, double latitude, double longitude) {
		this.description = description;
		this.latitude = latitude;
		this.longitude = longitude;
		this.point = GeometryUtil.createPoint(longitude, latitude);
	}

	public static Location of(String description, double latitude, double longitude) {
		return Location.builder()
			.description(description)
			.latitude(latitude)
			.longitude(longitude)
			.build();
	}
}
