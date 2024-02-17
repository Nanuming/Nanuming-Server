package gdsc.nanuming.locker.service;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gdsc.nanuming.item.entity.ItemStatus;
import gdsc.nanuming.locker.dto.request.OpenLockerRequest;
import gdsc.nanuming.locker.dto.response.OpenLockerResponse;
import gdsc.nanuming.locker.entity.Locker;
import gdsc.nanuming.locker.entity.LockerStatus;
import gdsc.nanuming.locker.repository.LockerRepository;
import gdsc.nanuming.redis.repository.ReservationCacheRepository;
import gdsc.nanuming.reservation.entity.ReservationStatus;
import gdsc.nanuming.reservation.repository.ReservationRepository;
import gdsc.nanuming.security.util.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LockerService {

	private final LockerRepository lockerRepository;
	private final ReservationRepository reservationRepository;
	private final ReservationCacheRepository reservationCacheRepository;

	public List<Locker> getOccupiedLockerList(Long locationId) {
		return lockerRepository.findByLocationIdAndStatusAndItemItemStatus(locationId,
			LockerStatus.OCCUPIED, ItemStatus.AVAILABLE);
	}

	@Transactional
	public OpenLockerResponse openLocker(OpenLockerRequest request) {
		Long lockerId = request.getLockerId();
		CustomUserDetails currentUserDetails = getCurrentUserDetails();
		boolean reservationExists =
			reservationCacheRepository.existsByMemberIdAndLockerId(currentUserDetails.getId(), lockerId);

		if (!reservationExists) {
			throw new IllegalArgumentException("No Reservation found.");
		}

		Locker locker = lockerRepository.findById(lockerId)
			.orElseThrow(() -> new IllegalArgumentException("No Locker found."));
		locker.getItem().changeSaveStatusToShared();
		locker.changeLockerStatusToEmpty();

		// TODO: need refactor here
		reservationRepository.findByMemberIdAndLockerIdAndReservationStatus(currentUserDetails.getId(), lockerId,
				ReservationStatus.VALID)
			.orElseThrow(() -> new IllegalArgumentException("No Reservation found."))
			.changeStatus(ReservationStatus.SHARED);

		reservationCacheRepository.deleteByMemberIdAndLockerId(currentUserDetails.getId(), lockerId);
		return OpenLockerResponse.from(lockerId);
	}

	private CustomUserDetails getCurrentUserDetails() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
			return (CustomUserDetails)authentication.getPrincipal();
		} else {
			throw new IllegalStateException("User is not authenticated.");
		}
	}
}


