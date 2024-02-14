package gdsc.nanuming.location.dto.request;

import lombok.Getter;

@Getter
public class NearLocationAndItemRequest {

	private double latitude;
	private double longitude;
	private double latitudeDelta;
	private double longitudeDelta;

}
