package gdsc.nanuming.category.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CategoryName {

	TOY("toy"),
	BOOK("book"),
	CLOTHES("clothes"),
	NURTURE_PRODUCT("nurture product"),
	ETC("etc");

	private final String name;

}
