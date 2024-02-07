package gdsc.nanuming.item.dto.request;

import java.util.List;

import gdsc.nanuming.item.entity.Item;
import gdsc.nanuming.locker.entity.Locker;
import gdsc.nanuming.member.entity.Member;
import lombok.Getter;

@Getter
public class AddItemRequest {

	private Long sharerId;
	private Long lockerId;
	private String title;
	private String description;
	private List<String> imageList;

	public Item toEntity(Member member, Locker locker) {
		return Item.of(
			member,
			locker,
			this.title,
			this.description
		);
	}
}
