package gdsc.nanuming.location.entity;

import java.util.List;

import gdsc.nanuming.locker.entity.Locker;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;

@Getter
@Entity
public class Location {

	@Id
	@Column(name = "location_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String description;

	private double latitude;

	private double longitude;

	@OneToMany(mappedBy = "location")
	private List<Locker> lockerList;
}
