package gdsc.nanuming.common;

import com.fasterxml.jackson.annotation.JsonInclude;

public class ResponseDto<T> {

	private final boolean success;
	private final int status;
	private final String message;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final T data;

}
