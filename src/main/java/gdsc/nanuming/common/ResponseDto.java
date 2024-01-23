package gdsc.nanuming.common;

import gdsc.nanuming.common.code.Code;

public class ResponseDto<T> {

	private boolean success;
	private int status;
	private String message;

	private ResponseDto(Code code) {
		this.success = code.isSuccess();
		this.status = code.getStatus();
		this.message = code.getMessage();
	}

}
