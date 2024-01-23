package gdsc.nanuming.common;

import gdsc.nanuming.common.code.Code;

public class ResponseDto<T> {

	private final boolean success;
	private final int status;
	private final String message;

	private ResponseDto(Code code) {
		this.success = code.isSuccess();
		this.status = code.getStatus();
		this.message = code.getMessage();
	}

	public static <T> ResponseDto<T> from(Code code) {
		return new ResponseDto<>(code);
	}
}
