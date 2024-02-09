package gdsc.nanuming.item.dto.request;

import java.util.List;

import gdsc.nanuming.category.entity.Category;
import gdsc.nanuming.item.entity.Item;
import gdsc.nanuming.member.entity.Member;
import lombok.Getter;

@Getter
public class AddItemRequest {

	private Long sharerId;
	private Long categoryId;
	private String title;
	private String description;
	private List<String> imageList;

	public Item toEntity(Member member, Category category) {
		return Item.of(
			member,
			category,
			this.title,
			this.description
		);
	}
}
