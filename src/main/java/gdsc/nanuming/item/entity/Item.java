package gdsc.nanuming.item.entity;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import gdsc.nanuming.category.entity.Category;
import gdsc.nanuming.common.BaseEntity;
import gdsc.nanuming.image.entity.ItemImage;
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

	@OneToOne
	@JoinColumn(name = "confirm_image_id")
	private ItemImage confirmItemImage;

	@OneToMany(mappedBy = "item")
	private List<ItemImage> itemImageList = new ArrayList<>();

	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;

	private String title;

	private String description;

	@JdbcTypeCode(SqlTypes.VARCHAR)
	private SaveStatus saveStatus = SaveStatus.TEMPORARY;

	private boolean shared = false;

	@Builder
	private Item(Member sharer, Category category, String title, String description) {
		this.sharer = sharer;
		this.category = category;
		this.title = title;
		this.description = description;
	}

	public static Item of(Member sharer, Category category, String title, String description) {
		return Item.builder()
			.sharer(sharer)
			.category(category)
			.title(title)
			.description(description)
			.build();
	}

	public void addMainItemImage(ItemImage itemImage) {
		this.mainItemImage = itemImage;
	}

	public void addConfirmItemImage(ItemImage itemImage) {
		this.confirmItemImage = itemImage;
	}

	public void addItemImageList(List<ItemImage> itemImageList) {
		itemImageList.forEach(itemImage -> {
			this.itemImageList.add(itemImage.addItem(this));
		});
	}

	public void assignLocker(Locker locker) {
		this.locker = locker.storeItem(this);
	}
}
