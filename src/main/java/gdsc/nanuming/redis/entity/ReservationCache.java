package gdsc.nanuming.redis.entity;

import org.springframework.data.annotation.Id;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ReservationCache {

	@Id
	private Long memberId;

	private Long lockerId;

	@Builder
	private ReservationCache(final Long memberId, final Long lockerId) {
		this.memberId = memberId;
		this.lockerId = lockerId;
	}

	public static ReservationCache of(Long memberId, Long lockerId) {
		return ReservationCache.builder()
			.memberId(memberId)
			.lockerId(lockerId)
			.build();
	}

}
