package gdsc.nanuming.item.dto.request;

import lombok.Getter;

@Getter
public class ShowNearItemListRequest {

	private double latitude;
	private double longitude;
	private double latitudeDelta;
	private double longitudeDelta;

}
