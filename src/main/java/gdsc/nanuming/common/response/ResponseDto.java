package gdsc.nanuming.common.response;

import gdsc.nanuming.common.code.Code;
import lombok.Getter;

@Getter
public class ResponseDto {

	private final boolean success;
	private final int status;
	private final String message;

	private ResponseDto(Code code) {
		this.success = code.isSuccess();
		this.status = code.getStatus();
		this.message = code.getMessage();
	}

	public static ResponseDto from(Code code) {
		return new ResponseDto(code);
	}
}
