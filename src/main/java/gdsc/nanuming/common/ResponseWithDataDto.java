package gdsc.nanuming.common;

import gdsc.nanuming.common.code.Code;
import lombok.Getter;

@Getter
public class ResponseWithDataDto<T> {

	private final boolean success;
	private final int status;
	private final String message;
	private final T data;

	private ResponseWithDataDto(Code code, T data) {
		this.success = code.isSuccess();
		this.status = code.getStatus();
		this.message = code.getMessage();
		this.data = data;
	}

	public static <T> ResponseWithDataDto<T> of(Code code, T data) {
		return new ResponseWithDataDto<>(code, data);
	}

}
