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

	@Column(name = "is_main")
	private boolean isMain;

	@Column(name = "is_confirm")
	private boolean isConfirm = false;

	@Builder
	private ItemImage(String itemImageUrl, boolean isMain) {
		this.itemImageUrl = itemImageUrl;
		this.isMain = isMain;
	}

	public static ItemImage from(String itemImageUrl, boolean isMain) {
		return ItemImage.builder()
			.itemImageUrl(itemImageUrl)
			.isMain(isMain)
			.build();
	}

	public void setAsMainImage() {
		this.isMain = true;
	}

	public void setAsConfirmImage() {
		this.isConfirm = true;
	}

	public ItemImage addItem(Item item) {
		this.item = item;
		return this;
	}
}
