package gdsc.nanuming.item.dto.request;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import gdsc.nanuming.category.entity.Category;
import gdsc.nanuming.item.entity.Item;
import gdsc.nanuming.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AddItemRequest {

	private Long sharerId;
	private Long categoryId;
	private String title;
	private String description;
	private List<MultipartFile> imageList;

	@Builder
	private AddItemRequest(Long sharerId, Long categoryId, String title, String description,
		List<MultipartFile> imageList) {
		this.sharerId = sharerId;
		this.categoryId = categoryId;
		this.title = title;
		this.description = description;
		this.imageList = imageList;
	}

	public Item toEntity(Member member, Category category) {
		return Item.of(
			member,
			category,
			this.title,
			this.description
		);
	}
}
