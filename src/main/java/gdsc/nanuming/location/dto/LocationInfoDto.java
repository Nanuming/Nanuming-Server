package gdsc.nanuming.location.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LocationInfoDto {

	private Long locationId;
	private double latitude;
	private double longitude;

	@Builder
	private LocationInfoDto(Long locationId, double latitude, double longitude) {
		this.locationId = locationId;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public static LocationInfoDto of(Long locationId, double latitude, double longitude) {
		return LocationInfoDto.builder()
			.locationId(locationId)
			.latitude(latitude)
			.longitude(longitude)
			.build();
	}
}
