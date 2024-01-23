package gdsc.nanuming.common;

import com.fasterxml.jackson.annotation.JsonInclude;

public class ResponseDto<T> {

	private boolean success;
	private int status;
	private String message;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final T data;

}
