package gdsc.nanuming.item.dto.request;

import gdsc.nanuming.common.UserMapPolygon;
import lombok.Getter;

@Getter
public class ShowNearItemListRequest implements UserMapPolygon {

	private double latitude;
	private double longitude;
	private double latitudeDelta;
	private double longitudeDelta;

}
