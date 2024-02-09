package gdsc.nanuming.common.response;

import gdsc.nanuming.common.code.Code;
import lombok.Getter;

@Getter
public class BaseResponseWithData<T> {

	private final boolean success;
	private final int status;
	private final String message;
	private final T data;

	private BaseResponseWithData(Code code, T data) {
		this.success = code.isSuccess();
		this.status = code.getStatus();
		this.message = code.getMessage();
		this.data = data;
	}

	public static <T> BaseResponseWithData<T> of(Code code, T data) {
		return new BaseResponseWithData<>(code, data);
	}

}
