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

	private String address;

	private String name;

	private double latitude;

	private double longitude;

	@Column(columnDefinition = "Point")
	private Point point;

	@OneToMany(mappedBy = "location")
	private List<Locker> lockerList;

	@Builder
	private Location(String address, String name, double latitude, double longitude) {
		this.address = address;
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
		this.point = GeometryUtil.createPoint(longitude, latitude);
	}

	public static Location of(String address, String name, double latitude, double longitude) {
		return Location.builder()
			.address(address)
			.name(name)
			.latitude(latitude)
			.longitude(longitude)
			.build();
	}

	public String convertPointToText() {
		return String.format("POINT(%s %s)", this.longitude, this.latitude);
	}
}
