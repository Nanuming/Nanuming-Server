package gdsc.nanuming.item.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ItemStatus {

	TEMPORARY("temporary"),
	AVAILABLE("available"),
	RESERVED("reserved"),
	SHARED("shared");

	private final String value;

}
