package gdsc.nanuming.category.entity;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import gdsc.nanuming.item.entity.Item;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {

	@Id
	@Column(name = "category_id")
	private Long id;

	@JdbcTypeCode(SqlTypes.VARCHAR)
	private CategoryName categoryName;

	@OneToMany(mappedBy = "category")
	private List<Item> itemList = new ArrayList<>();

}
