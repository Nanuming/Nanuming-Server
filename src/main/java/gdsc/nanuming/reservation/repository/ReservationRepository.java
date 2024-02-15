package gdsc.nanuming.reservation.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import gdsc.nanuming.reservation.entity.Reservation;
import gdsc.nanuming.reservation.entity.ReservationStatus;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

	Optional<Reservation> findByMemberIdAndLockerIdAndReservationStatus(Long memberId, Long lockerId,
		ReservationStatus reservationStatus);

}
