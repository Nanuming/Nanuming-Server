package gdsc.nanuming.item.entity;

import gdsc.nanuming.common.BaseEntity;
import gdsc.nanuming.item.SaveStatus;
import gdsc.nanuming.member.entity.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
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

	@Column(name = "main_image_id")
	private Long mainImageId;

	@Column(name = "locker_id")
	private Long lockerId;

	private String title;

	private String description;

	private SaveStatus saveStatus = SaveStatus.TEMPORARY;

	private boolean shared = false;

}
