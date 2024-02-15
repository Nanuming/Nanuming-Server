package gdsc.nanuming.reservation.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gdsc.nanuming.locker.entity.Locker;
import gdsc.nanuming.locker.repository.LockerRepository;
import gdsc.nanuming.member.entity.Member;
import gdsc.nanuming.member.repository.MemberRepository;
import gdsc.nanuming.redis.entity.ReservationCache;
import gdsc.nanuming.redis.repository.ReservationCacheRepository;
import gdsc.nanuming.reservation.dto.request.ReservationRequest;
import gdsc.nanuming.reservation.dto.response.ReservationResponse;
import gdsc.nanuming.reservation.entity.Reservation;
import gdsc.nanuming.reservation.entity.ReservationStatus;
import gdsc.nanuming.reservation.repository.ReservationRepository;
import gdsc.nanuming.security.util.CustomUserDetails;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationService {

	private final LockerRepository lockerRepository;
	private final MemberRepository memberRepository;
	private final ReservationRepository reservationRepository;
	private final ReservationCacheRepository reservationCacheRepository;

	@Transactional
	public void changeStatusToExpired(Long memberId, Long lockerId) {
		Reservation reservation = reservationRepository.findByMemberIdAndLockerId(memberId, lockerId)
			.orElseThrow(() -> new IllegalArgumentException("No Reservation found."));
		reservation.changeStatus(ReservationStatus.EXPIRED);

		Locker locker = lockerRepository.findById(lockerId)
			.orElseThrow(() -> new IllegalArgumentException("No Locker found."));

		locker.getItem().changeSaveStatusToAvailable();
	}

	@Transactional
	public ReservationResponse reserveLocker(ReservationRequest request) {
		CustomUserDetails currentUserDetails = getCurrentUserDetails();
		Member member = memberRepository.findById(currentUserDetails.getId())
			.orElseThrow(() -> new IllegalArgumentException("No Member found."));
		Locker locker = lockerRepository.findById(request.getLockerId())
			.orElseThrow(() -> new IllegalArgumentException("No Locker found."));

		if (locker.getItem() == null) {
			throw new IllegalArgumentException("Locker is empty.");
		}

		Reservation reservation = reservationRepository.save(Reservation.of(member, locker));
		reservationCacheRepository.save(ReservationCache.of(member.getId(), locker.getId()));
		locker.getItem().changeSaveStatusToReserved();

		return ReservationResponse.of(reservation.getId(), member.getId(), locker.getId());
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
