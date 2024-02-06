package gdsc.nanuming.locker;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LockerSize {

	SMALL("small"),
	MIDDLE("middle"),
	BIG("big");

	private final String size;

}
