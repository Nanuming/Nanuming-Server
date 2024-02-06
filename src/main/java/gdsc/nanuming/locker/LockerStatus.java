package gdsc.nanuming.locker;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LockerStatus {

	EMPTY("empty"),
	CONTAINED("contained");

	private final String status;

}
