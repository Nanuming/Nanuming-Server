package gdsc.nanuming.reservation.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import gdsc.nanuming.reservation.entity.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

	Optional<Reservation> findByMemberIdAndLockerId(Long memberId, Long lockerId);

}
