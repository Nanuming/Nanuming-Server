package gdsc.nanuming.locker.entity;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import gdsc.nanuming.item.entity.Item;
import gdsc.nanuming.location.entity.Location;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Locker {

	@Id
	@Column(name = "locker_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "location_id")
	private Location location;

	@JdbcTypeCode(SqlTypes.VARCHAR)
	private LockerSize size;

	@JdbcTypeCode(SqlTypes.VARCHAR)
	private LockerStatus status = LockerStatus.EMPTY;

	@OneToOne(mappedBy = "locker")
	private Item item;

	@Builder
	private Locker(Location location, LockerSize size) {
		this.location = location;
		this.size = size;
	}

	public static Locker of(Location location, LockerSize size) {
		return Locker.builder()
			.location(location)
			.size(size)
			.build();
	}
}
