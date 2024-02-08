package gdsc.nanuming.location.openapi.dto;

public class LocationApiDto {

	private long stCode;
	private long zipCode;
	private String name;
	private String address;
	private double latitude;
	private double longitude;

	private LocationApiDto(long stCode, long zipCode, String name, String address, double latitude, double longitude) {
		this.stCode = stCode;
		this.zipCode = zipCode;
		this.name = name;
		this.address = address;
		this.latitude = latitude;
		this.longitude = longitude;
	}

}
