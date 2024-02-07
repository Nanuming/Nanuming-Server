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
	private String itemImageUrl;

	@Builder
	private ItemImage(String itemImageUrl) {
		this.itemImageUrl = itemImageUrl;
	}

	public static ItemImage from(String itemImageUrl) {
		return ItemImage.builder()
			.itemImageUrl(itemImageUrl)
			.build();
	}
}
