package gdsc.nanuming.image.entity;

import gdsc.nanuming.common.BaseEntity;
import gdsc.nanuming.item.entity.Item;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemImage extends BaseEntity {

	@Id
	@Column(name = "item_image_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long itemImageId;

	@ManyToOne
	@JoinColumn(name = "item_id")
	private Item item;

	@Column(name = "image_url")
	private String imageUrl;

	@Builder
	private ItemImage(Item item, String imageUrl) {
		this.item = item;
		this.imageUrl = imageUrl;
	}

	public static ItemImage of(Item item, String imageUrl) {
		return ItemImage.builder()
			.item(item)
			.imageUrl(imageUrl)
			.build();
	}
}
