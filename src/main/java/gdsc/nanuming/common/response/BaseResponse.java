package gdsc.nanuming.common.response;

import gdsc.nanuming.common.code.Code;
import lombok.Getter;

@Getter
public class BaseResponse {

	private final boolean success;
	private final int status;
	private final String message;

	private BaseResponse(Code code) {
		this.success = code.isSuccess();
		this.status = code.getStatus();
		this.message = code.getMessage();
	}

	public static BaseResponse from(Code code) {
		return new BaseResponse(code);
	}
}
