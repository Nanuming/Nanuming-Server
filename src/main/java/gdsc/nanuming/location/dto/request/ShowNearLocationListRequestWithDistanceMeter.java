package gdsc.nanuming.location.dto.request;

import lombok.Getter;

@Getter
public class ShowNearLocationListRequestWithDistanceMeter {

	private double latitude;
	private double longitude;
	private double radiusInKm;
}
