package gdsc.nanuming.item.entity;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import gdsc.nanuming.common.BaseEntity;
import gdsc.nanuming.image.entity.ItemImage;
import gdsc.nanuming.item.SaveStatus;
import gdsc.nanuming.locker.entity.Locker;
import gdsc.nanuming.member.entity.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item extends BaseEntity {

	@Id
	@Column(name = "item_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "sharer_id")
	private Member sharer;

	@OneToOne
	@JoinColumn(name = "locker_id")
	private Locker locker;

	@OneToOne
	@JoinColumn(name = "main_image_id")
	private ItemImage mainItemImage;

	@OneToMany(mappedBy = "item")
	private List<ItemImage> itemImageList = new ArrayList<>();

	private String title;

	private String description;

	@JdbcTypeCode(SqlTypes.VARCHAR)
	private SaveStatus saveStatus = SaveStatus.TEMPORARY;

	private boolean shared = false;

	@Builder
	private Item(Member sharer, Locker locker, String title, String description) {
		this.sharer = sharer;
		this.locker = locker;
		this.title = title;
		this.description = description;
	}

	public static Item of(Member sharer, Locker locker, String title, String description) {
		return Item.builder()
			.sharer(sharer)
			.locker(locker)
			.title(title)
			.description(description)
			.build();
	}

	public void addMainItemImage(ItemImage itemImage) {
		this.mainItemImage = itemImage;
	}

	public void addItemImageList(List<ItemImage> itemImageList) {
		itemImageList.forEach(itemImage -> {
			this.itemImageList.add(itemImage.addItem(this));
		});
	}
}
