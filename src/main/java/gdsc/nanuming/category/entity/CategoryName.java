package gdsc.nanuming.category.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CategoryName {

	TOY("장난감"),
	BOOK("도서"),
	CLOTHES("의류"),
	NURTURE_PRODUCT("육아용품"),
	ETC("기타");

	private final String name;

}
