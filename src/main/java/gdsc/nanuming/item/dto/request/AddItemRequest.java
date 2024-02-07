package gdsc.nanuming.item.dto.request;

import java.util.List;

import lombok.Getter;

@Getter
public class AddItemRequest {

	private Long sharerId;
	private Long lockerId;
	private String title;
	private String description;
	private List<String> imageList;

}
