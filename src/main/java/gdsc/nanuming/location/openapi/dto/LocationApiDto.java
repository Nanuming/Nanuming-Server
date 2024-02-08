package gdsc.nanuming.location.openapi.dto;

import gdsc.nanuming.location.entity.Location;
import lombok.Builder;
import lombok.Getter;

@Getter
public class LocationApiDto {

	private long stCode;
	private long zipCode;
	private String name;
	private String address;
	private double latitude;
	private double longitude;

	@Builder
	private LocationApiDto(long stCode, long zipCode, String name, String address, double latitude, double longitude) {
		this.stCode = stCode;
		this.zipCode = zipCode;
		this.name = name;
		this.address = address;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public static LocationApiDto of(long stCode, long zipCode, String name, String address, double latitude,
		double longitude) {
		return LocationApiDto.builder()
			.stCode(stCode)
			.zipCode(zipCode)
			.name(name)
			.address(address)
			.latitude(latitude)
			.longitude(longitude)
			.build();
	}

	public Location toEntity() {
		return Location.of(
			createDescription(),
			this.latitude,
			this.longitude
		);
	}

}
