package gdsc.nanuming.reservation.entity;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import gdsc.nanuming.locker.entity.Locker;
import gdsc.nanuming.member.entity.Member;
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

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation {

	@Id
	@Column(name = "reservation_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;

	@ManyToOne
	@JoinColumn(name = "locker_id")
	private Locker locker;

	@JdbcTypeCode(SqlTypes.VARCHAR)
	private ReservationStatus reservationStatus = ReservationStatus.VALID;

	@Builder
	private Reservation(Member member, Locker locker) {
		this.member = member;
		this.locker = locker;
	}

	public static Reservation of(Member member, Locker locker) {
		return Reservation.builder()
			.member(member)
			.locker(locker)
			.build();
	}

	public void changeStatus(ReservationStatus status) {
		this.reservationStatus = status;
	}
}
