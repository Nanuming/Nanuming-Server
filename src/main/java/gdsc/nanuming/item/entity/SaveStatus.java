package gdsc.nanuming.item.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SaveStatus {

	TEMPORARY("temporary"),
	SAVED("saved");

	private final String value;

}
