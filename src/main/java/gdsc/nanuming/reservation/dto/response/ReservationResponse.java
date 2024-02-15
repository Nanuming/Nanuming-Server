package gdsc.nanuming.reservation.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ReservationResponse {

	private Long reservationId;
	private Long memberId;
	private Long lockerId;

	@Builder
	private ReservationResponse(Long reservationId, Long memberId, Long lockerId) {
		this.reservationId = reservationId;
		this.memberId = memberId;
		this.lockerId = lockerId;
	}
}
