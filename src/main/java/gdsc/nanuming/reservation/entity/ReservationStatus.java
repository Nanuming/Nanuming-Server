package gdsc.nanuming.reservation.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReservationStatus {

	VALID("valid"),
	SHARED("shared"),
	EXPIRED("expired");

	private final String status;

}
