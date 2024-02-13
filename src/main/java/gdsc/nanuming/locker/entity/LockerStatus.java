package gdsc.nanuming.locker.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LockerStatus {

	EMPTY("empty"),
	OCCUPIED("occupied");

	private final String status;

}
